package com.lazy.tcc.core.propagator.support;

import com.lazy.tcc.core.propagator.IdempotentContextPropagator;

/**
 * <p>
 * AbstractTransactionContextPropagator Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public abstract class AbstractIdempotentContextPropagator implements IdempotentContextPropagator {

    protected static final String IDEMPOTENT_PROPAGATOR_KEY = "LAZY_TCC_IDEMPOTENT_ID";


}
