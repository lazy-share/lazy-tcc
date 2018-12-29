package com.lazy.tcc.core.propagator;

import com.lazy.tcc.core.exception.TransactionManagerException;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public final class IdempotentContextPropagatorSingleFactory {

    private final static ConcurrentHashMap<Class<? extends IdempotentContextPropagator>,
            IdempotentContextPropagator> IDEMPOTENT_CONTEXT_PROPAGATOR_CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();


    public static IdempotentContextPropagator create(Class<? extends IdempotentContextPropagator> clazz) {

        if (IDEMPOTENT_CONTEXT_PROPAGATOR_CONCURRENT_HASH_MAP.isEmpty() ||
                IDEMPOTENT_CONTEXT_PROPAGATOR_CONCURRENT_HASH_MAP.get(clazz) == null) {

            try {

                IdempotentContextPropagator propagator = clazz.newInstance();
                IDEMPOTENT_CONTEXT_PROPAGATOR_CONCURRENT_HASH_MAP.put(clazz, propagator);
            } catch (Exception e) {
                throw new TransactionManagerException("Instantiating transaction propagator exceptions", e);
            }
        }

        return IDEMPOTENT_CONTEXT_PROPAGATOR_CONCURRENT_HASH_MAP.get(clazz);
    }
}
