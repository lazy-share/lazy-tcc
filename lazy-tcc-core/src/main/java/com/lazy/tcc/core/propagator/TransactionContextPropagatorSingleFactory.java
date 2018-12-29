package com.lazy.tcc.core.propagator;

import com.lazy.tcc.core.exception.TransactionManagerException;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public final class TransactionContextPropagatorSingleFactory {

    private final static ConcurrentHashMap<Class<? extends TransactionContextPropagator>,
            TransactionContextPropagator> PROPAGATOR_HOLDER = new ConcurrentHashMap<>();


    public static TransactionContextPropagator create(Class<? extends TransactionContextPropagator> clazz) {

        if (PROPAGATOR_HOLDER.isEmpty() || PROPAGATOR_HOLDER.get(clazz) == null) {

            try {

                TransactionContextPropagator propagator = clazz.newInstance();
                PROPAGATOR_HOLDER.put(clazz, propagator);
            } catch (Exception e) {
                throw new TransactionManagerException("Instantiating transaction propagator exceptions", e);
            }
        }

        return PROPAGATOR_HOLDER.get(clazz);
    }
}
