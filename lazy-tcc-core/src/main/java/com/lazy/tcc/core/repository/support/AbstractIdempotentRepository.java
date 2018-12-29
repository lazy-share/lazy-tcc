package com.lazy.tcc.core.repository.support;

import com.lazy.tcc.core.entity.IdempotentEntity;

/**
 * <p>
 * AbstractTransactionRepository
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/18.
 */
public abstract class AbstractIdempotentRepository extends AbstractDataSourceRepository<IdempotentEntity, IdempotentEntity.IdempotentPk> {


}
