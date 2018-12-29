package com.lazy.tcc.core;

import com.alibaba.fastjson.JSON;
import com.lazy.tcc.common.enums.TransactionPhase;
import com.lazy.tcc.core.entity.ParticipantEntity;
import com.lazy.tcc.core.entity.TransactionEntity;
import com.lazy.tcc.core.exception.CancelException;
import com.lazy.tcc.core.exception.ConfirmException;
import com.lazy.tcc.core.exception.TransactionManagerException;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.mapper.ParticipantMapper;
import com.lazy.tcc.core.mapper.TransactionMapper;
import com.lazy.tcc.core.propagator.TransactionContextPropagator;
import com.lazy.tcc.core.propagator.TransactionContextPropagatorSingleFactory;
import com.lazy.tcc.core.repository.ParticipantRepositoryFactory;
import com.lazy.tcc.core.repository.TransactionRepositoryFactory;
import com.lazy.tcc.core.repository.support.AbstractParticipantRepository;
import com.lazy.tcc.core.repository.support.AbstractTransactionRepository;
import com.lazy.tcc.core.threadpool.SysDefaultThreadPool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * TransactionManager Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public final class TransactionManager {

    private final static Logger LOGGER = LoggerFactory.getLogger(TransactionManager.class);
    private static final ThreadLocal<LinkedList<Long>> CURRENT_THREAD_TRANSACTION_ID_HOLDER = new ThreadLocal<>();
    private static final AbstractTransactionRepository TRANSACTION_REPOSITORY = TransactionRepositoryFactory.create();
    private static final AbstractParticipantRepository PARTICIPANT_REPOSITORY = ParticipantRepositoryFactory.create();

    private static TransactionManager single;

    private TransactionManager() {
    }

    public static TransactionManager getSingle() {
        if (single == null) {
            synchronized (TransactionManager.class) {
                if (single == null) {
                    single = new TransactionManager();
                }
            }
        }
        return single;
    }

    public Transaction begin() {

        Transaction transaction = new Transaction().init();
        TRANSACTION_REPOSITORY.insert(TransactionMapper.INSTANCE.to(transaction));
        push(transaction.getTxId());

        return transaction;
    }

    private void push(Long txId) {
        if (CURRENT_THREAD_TRANSACTION_ID_HOLDER.get() == null) {
            CURRENT_THREAD_TRANSACTION_ID_HOLDER.set(new LinkedList<>());
        }
        CURRENT_THREAD_TRANSACTION_ID_HOLDER.get().push(txId);
    }

    public void commit(boolean asyncCommit) {

        final Transaction transaction = getCurrentTransaction();

        if (transaction == null) {
            throw new TransactionManagerException("not exists active transaction in commit operation");
        }

        transaction.setTxPhase(TransactionPhase.CONFIRM);
        TRANSACTION_REPOSITORY.update(TransactionMapper.INSTANCE.to(transaction));

        if (asyncCommit) {

            try {

                long statTime = System.currentTimeMillis();

                SysDefaultThreadPool.executeIoCrowdThreadTask(() -> commitTransaction(transaction));

                LOGGER.debug("async submit cost time:" + (System.currentTimeMillis() - statTime));
            } catch (Throwable commitException) {

                LOGGER.warn("compensable transaction async submit confirm failed, recovery job will try to confirm later.", commitException);
                throw new ConfirmException(commitException);
            }
        } else {

            commitTransaction(transaction);
        }
    }

    private void commitTransaction(Transaction transaction) {

        TransactionContext context = this.getLocalTransactionContext();

        if (context == null) {
            throw new TransactionManagerException("not exists active context in commit operation");
        }

        if (!context.getTxId().equals(transaction.getTxId())) {

            throw new TransactionManagerException(
                    String.format("current context [%s] txId not match current transaction  [%s] txId in commit operation"
                            , JSON.toJSONString(context), JSON.toJSONString(transaction))
            );
        }

        List<Participant> allParticipants = this.getParticipants(transaction);

        for (Participant participant : allParticipants) {

            if (!participant.getTxId().equals(transaction.getTxId())) {

                throw new TransactionManagerException(
                        String.format("participant [%s] txId not match current transaction  [%s] txId in commit operation"
                                , JSON.toJSONString(participant), JSON.toJSONString(transaction))
                );
            }

            //confirm phase
            participant.confirm(context);
        }

        TRANSACTION_REPOSITORY.delete(transaction.getTxId());
    }

    public void rollback(boolean asyncCancel) {

        final Transaction transaction = getCurrentTransaction();

        if (transaction == null) {
            throw new TransactionManagerException("not exists active transaction in rollback operation");
        }

        transaction.setTxPhase(TransactionPhase.CANCEL);

        //If this step is unsuccessful, the timer compensates for rollback
        TRANSACTION_REPOSITORY.update(TransactionMapper.INSTANCE.to(transaction));

        if (asyncCancel) {

            try {

                SysDefaultThreadPool.executeIoCrowdThreadTask(() -> rollbackTransaction(transaction));

            } catch (Throwable rollbackException) {

                LOGGER.warn("compensable transaction async rollback failed, recovery job will try to rollback later.", rollbackException);
                throw new CancelException(rollbackException);
            }
        } else {

            rollbackTransaction(transaction);
        }
    }

    private void rollbackTransaction(Transaction transaction) {

        TransactionContext context = this.getLocalTransactionContext();

        if (context == null) {
            throw new TransactionManagerException("not exists active context in rollback operation");
        }

        if (!context.getTxId().equals(transaction.getTxId())) {

            throw new TransactionManagerException(
                    String.format("current context [%s] txId not match current transaction  [%s] txId in rollback operation"
                            , JSON.toJSONString(context), JSON.toJSONString(transaction))
            );
        }

        List<Participant> allParticipants = this.getParticipants(transaction);
        for (Participant participant : allParticipants) {

            if (!participant.getTxId().equals(transaction.getTxId())) {

                throw new TransactionManagerException(
                        String.format("participant [%s] txId not match current transaction  [%s] txId in rollback operation"
                                , JSON.toJSONString(participant), JSON.toJSONString(transaction))
                );
            }

            participant.cancel(context);
        }

        TRANSACTION_REPOSITORY.delete(transaction.getTxId());
    }

    public void participant(Participant participant) {
        PARTICIPANT_REPOSITORY.insert(ParticipantMapper.INSTANCE.to(participant));
    }

    public boolean hasDistributedActiveTransaction(WeavingPointInfo pointInfo) {
        if (this.hasLocalActiveTransaction()) {
            return true;
        }
        return this.getTransactionPropagator(pointInfo).getContext() != null;
    }

    public boolean hasLocalActiveTransaction() {
        return CURRENT_THREAD_TRANSACTION_ID_HOLDER.get() != null && !CURRENT_THREAD_TRANSACTION_ID_HOLDER.get().isEmpty();
    }

    public Transaction getCurrentTransaction() {
        if (hasLocalActiveTransaction()) {
            Long txId = CURRENT_THREAD_TRANSACTION_ID_HOLDER.get().peek();
            TransactionEntity entity = TRANSACTION_REPOSITORY.findById(txId);
            return entity == null ? null : TransactionMapper.INSTANCE.from(entity);
        }
        return null;
    }

    public void cleanCurrentTransaction(Transaction transaction) {
        if (hasLocalActiveTransaction()) {
            Transaction curTransaction = this.getCurrentTransaction();
            if (curTransaction == transaction) {
                CURRENT_THREAD_TRANSACTION_ID_HOLDER.get().pop();

                if (CURRENT_THREAD_TRANSACTION_ID_HOLDER.get().isEmpty()) {
                    CURRENT_THREAD_TRANSACTION_ID_HOLDER.remove();
                }

                if (this.getParticipants(transaction).isEmpty()) {
                    TRANSACTION_REPOSITORY.delete(transaction.getTxId());
                }
            }
        }
    }

    public List<Participant> getParticipants(Transaction transaction) {

        List<Participant> participants = new ArrayList<>();
        List<ParticipantEntity> participantEntities = PARTICIPANT_REPOSITORY.findByTxId(transaction.getTxId());

        participantEntities.forEach(participantEntity -> participants.add(ParticipantMapper.INSTANCE.from(participantEntity)));

        return participants;
    }

    public TransactionContext getLocalTransactionContext() {

        Transaction transaction = this.getCurrentTransaction();

        if (transaction == null) {
            return null;
        }

        return new TransactionContext()
                .setTxId(transaction.getTxId())
                .setTxPhase(transaction.getTxPhase());

    }

    public Transaction getDistributedTransaction(WeavingPointInfo pointInfo) {
        TransactionContext context = this.getDistributedTransactionContext(pointInfo);
        if (context == null) {
            return null;
        }
        TransactionEntity entity = TRANSACTION_REPOSITORY.findById(context.getTxId());
        return entity == null ? null : TransactionMapper.INSTANCE.from(entity);
    }


    public TransactionContext getDistributedTransactionContext(WeavingPointInfo pointInfo) {

        TransactionContext context = this.getLocalTransactionContext();

        if (context == null) {

            //if not found in current thread local, try to retrieve it from the transactional propagator
            return this.getTransactionPropagator(pointInfo).getContext();
        }

        return context;

    }

    public TransactionContextPropagator getTransactionPropagator(WeavingPointInfo pointInfo) {

        Class<? extends TransactionContextPropagator> cls = pointInfo.getCompensable().propagator();

        return TransactionContextPropagatorSingleFactory.create(cls);
    }


}
