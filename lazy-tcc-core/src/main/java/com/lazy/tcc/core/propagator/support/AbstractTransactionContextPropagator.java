package com.lazy.tcc.core.propagator.support;

import com.lazy.tcc.core.propagator.TransactionContextPropagator;

/**
 * <p>
 * AbstractTransactionContextPropagator Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public abstract class AbstractTransactionContextPropagator implements TransactionContextPropagator {

    protected static final String TX_PROPAGATOR_KEY = "LAZY_TCC_TX_ID";



}
