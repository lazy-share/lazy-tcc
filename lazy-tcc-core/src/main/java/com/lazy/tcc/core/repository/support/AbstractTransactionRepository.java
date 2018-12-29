package com.lazy.tcc.core.repository.support;

import com.lazy.tcc.core.entity.TransactionEntity;
import com.lazy.tcc.core.repository.ParticipantRepositoryFactory;

import java.util.List;

/**
 * <p>
 * AbstractTransactionRepository
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/18.
 */
public abstract class AbstractTransactionRepository extends AbstractDataSourceRepository<TransactionEntity, Long> {

    protected static final AbstractParticipantRepository PARTICIPANT_REPOSITORY = ParticipantRepositoryFactory.create();

    @Override
    public int createTable() {
        return 0;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    public abstract List<TransactionEntity> findAllFailure();

}
