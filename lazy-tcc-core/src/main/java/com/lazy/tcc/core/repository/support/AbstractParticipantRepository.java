package com.lazy.tcc.core.repository.support;

import com.lazy.tcc.core.entity.ParticipantEntity;

import java.util.List;

/**
 * <p>
 * AbstractParticipantRepository
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/18.
 */
public abstract class AbstractParticipantRepository extends AbstractDataSourceRepository<ParticipantEntity, Long> {


    public abstract List<ParticipantEntity> findByTxId(Long txId);

    public abstract int deleteByTxId(Long txId);

}
