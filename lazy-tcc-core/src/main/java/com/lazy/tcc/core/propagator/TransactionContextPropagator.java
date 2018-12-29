package com.lazy.tcc.core.propagator;


import com.lazy.tcc.core.TransactionContext;

/**
 * <p>
 * TransactionContextPropagator Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
public interface TransactionContextPropagator {


    /**
     * set transaction propagator
     *
     * @param context transaction context
     */
    void setContext(TransactionContext context);

    /**
     * get transaction propagator
     *
     * @return {@link TransactionContext}
     */
    TransactionContext getContext();


}
