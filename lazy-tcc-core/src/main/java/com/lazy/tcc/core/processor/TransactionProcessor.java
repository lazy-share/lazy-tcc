package com.lazy.tcc.core.processor;

import com.lazy.tcc.common.enums.Propagation;
import com.lazy.tcc.common.enums.TransactionPhase;
import com.lazy.tcc.core.Transaction;
import com.lazy.tcc.core.TransactionContext;
import com.lazy.tcc.core.WeavingPointInfo;
import com.lazy.tcc.core.processor.support.AbstractProcessor;
import com.lazy.tcc.core.propagator.TransactionContextPropagatorSingleFactory;

/**
 * <p>
 * TransactionProcessor Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
public final class TransactionProcessor extends AbstractProcessor {

    /**
     * distribute transaction core processor
     */
    private static volatile TransactionProcessor single;

    /**
     * single
     *
     * @return self single instance
     */
    public static TransactionProcessor getSingle() {
        if (single == null) {
            synchronized (TransactionProcessor.class) {
                if (single == null) {
                    single = new TransactionProcessor();
                }
            }
        }
        return single;
    }

    /**
     * private construct
     */
    private TransactionProcessor() {

    }

    private ParticipantProcessor participantProcessor = ParticipantProcessor.getSingle();

    /**
     * impl is accept logic
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @return {@link Boolean}
     */
    @Override
    protected boolean isAccept(WeavingPointInfo pointInfo) {

        if (pointInfo.getCompensable().propagation().equals(Propagation.NOT_SUPPORTED)) {

            //not support transaction run model
            return false;
        }

        TransactionContext context = this.transactionManager.getDistributedTransactionContext(pointInfo);

        if (context == null) {

            return true;
        }

        return TransactionPhase.TRY.equals(context.getTxPhase());

    }

    /**
     * processor
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @return {@link Object}
     * @throws Throwable
     */
    @Override
    public Object processor(WeavingPointInfo pointInfo) throws Throwable {

        if (!isAccept(pointInfo)) {

            //not accept
            return pointInfo.getJoinPoint().proceed();
        }

        //propagator transaction
        TransactionContext context = this.transactionManager.getDistributedTransactionContext(pointInfo);
        TransactionContextPropagatorSingleFactory.create(pointInfo.getCompensable().propagator()).setContext(context);

        boolean isNewTransaction = this.isNewTransaction(pointInfo);
        if (isNewTransaction) {

            //new transaction
            return this.doProcessor(pointInfo);
        }

        //participant transaction
        return participantProcessor.processor(pointInfo);
    }

    /**
     * new transaction logic
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @return {@link Object}
     * @throws Throwable
     */
    @Override
    protected Object doProcessor(WeavingPointInfo pointInfo) throws Throwable {

        Object invokeVal;
        Transaction transaction = null;

        try {

            //begin transaction
            transaction = this.transactionManager.begin();

            try {

                //execute program
                invokeVal = pointInfo.getJoinPoint().proceed();
            } catch (Throwable tryException) {

                logger.error("program exception, rollback transaction");
                this.transactionManager.rollback(pointInfo.getCompensable().asyncCancel());

                throw tryException;
            }

            //program execute success, commit transaction
            this.transactionManager.commit(pointInfo.getCompensable().asyncConfirm());
        } catch (Throwable transactionException) {

            logger.error("transaction exception, clean current transaction, Give job compensation transaction rollback", transactionException);

            throw transactionException;
        } finally {

            this.transactionManager.cleanCurrentTransaction(transaction);
        }

        return invokeVal;
    }

    /**
     * is new transaction
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @return {@link Boolean}
     */
    private boolean isNewTransaction(WeavingPointInfo pointInfo) {

        TransactionContext context = this.transactionManager.getDistributedTransactionContext(pointInfo);
        if (context == null) {
            return true;
        }

        return TransactionPhase.TRY.equals(context.getTxPhase()) &&
                Propagation.REQUIRES_NEW.equals(pointInfo.getCompensable().propagation());

    }

    private boolean isNotSupportedTransaction(WeavingPointInfo pointInfo) {

        return pointInfo.getCompensable().propagation().equals(Propagation.NOT_SUPPORTED);
    }
}
