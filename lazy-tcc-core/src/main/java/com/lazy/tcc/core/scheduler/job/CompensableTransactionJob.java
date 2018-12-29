package com.lazy.tcc.core.scheduler.job;

import com.lazy.tcc.common.enums.TransactionPhase;
import com.lazy.tcc.core.TransactionContext;
import com.lazy.tcc.core.entity.ParticipantEntity;
import com.lazy.tcc.core.entity.TransactionEntity;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.mapper.ParticipantMapper;
import com.lazy.tcc.core.repository.ParticipantRepositoryFactory;
import com.lazy.tcc.core.repository.TransactionRepositoryFactory;
import com.lazy.tcc.core.repository.support.AbstractParticipantRepository;
import com.lazy.tcc.core.repository.support.AbstractTransactionRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * <p>
 * CompensableTransactionJob Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/18.
 */
public class CompensableTransactionJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompensableTransactionJob.class);
    private static final AbstractTransactionRepository TRANSACTION_REPOSITORY = TransactionRepositoryFactory.create();
    private static final AbstractParticipantRepository PARTICIPANT_REPOSITORY = ParticipantRepositoryFactory.create();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        long beginTime = System.currentTimeMillis();
        doExecute();
        long endTime = System.currentTimeMillis();

        long consumingTime = endTime - beginTime;
        LOGGER.info("CompensableTransactionJob Execute Finished: Consuming Time: " + consumingTime / 1000 + "s");
    }

    private void doExecute() {

        List<TransactionEntity> failures = TRANSACTION_REPOSITORY.findAllFailure();

        if (failures.isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Not Failures TCC Transaction");
            }
            return;
        }

        for (TransactionEntity entity : failures) {

            try {

                List<ParticipantEntity> participantEntities = PARTICIPANT_REPOSITORY.findByTxId(entity.getTxId());

                if (participantEntities.isEmpty()) {

                    TRANSACTION_REPOSITORY.delete(entity.getTxId());
                    continue;
                }

                final TransactionContext context = new TransactionContext()
                        .setTxId(entity.getTxId())
                        .setTxPhase(entity.getTxPhase());

                if (entity.getTxPhase().equals(TransactionPhase.TRY)) {

                    participantEntities.forEach(participantEntity -> ParticipantMapper.INSTANCE.from(participantEntity)
                            .getCancelMethodInvoker().invoker(context));
                } else if (entity.getTxPhase().equals(TransactionPhase.CONFIRM)) {

                    participantEntities.forEach(participantEntity -> ParticipantMapper.INSTANCE.from(participantEntity)
                            .getConfirmMethodInvoker().invoker(context));
                } else if (entity.getTxPhase().equals(TransactionPhase.CANCEL)) {

                    participantEntities.forEach(participantEntity -> ParticipantMapper.INSTANCE.from(participantEntity)
                            .getCancelMethodInvoker().invoker(context));
                }

                TRANSACTION_REPOSITORY.delete(entity.getTxId());
            } catch (Exception ex) {

                LOGGER.error("CompensableTransactionJob Execute Exception: ", ex);
            }

        }

    }
}
