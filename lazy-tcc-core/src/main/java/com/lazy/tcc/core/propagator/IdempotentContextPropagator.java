package com.lazy.tcc.core.propagator;


import com.lazy.tcc.core.IdempotentContext;

/**
 * <p>
 * TransactionContextPropagator Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
public interface IdempotentContextPropagator {


    /**
     * set
     *
     * @param idempotentContext {@link IdempotentContext}
     */
    void setIdempotentContext(IdempotentContext idempotentContext);

    /**
     * get
     *
     * @return {@link IdempotentContext}
     */
    IdempotentContext getIdempotentContext();


}
