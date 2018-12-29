package com.lazy.tcc.core.repository;

import com.lazy.tcc.core.repository.support.AbstractIdempotentRepository;
import com.lazy.tcc.core.spi.SpiConfiguration;

/**
 * <p>
 * TransactionRepositoryFactory Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public final class IdempotentRepositoryFactory {


    private IdempotentRepositoryFactory() {
    }

    private static volatile AbstractIdempotentRepository repository;

    public static AbstractIdempotentRepository create() {
        if (repository == null) {
            synchronized (IdempotentRepositoryFactory.class) {
                if (repository == null) {
                    try {
                        repository = SpiConfiguration.getInstance().getIdempotentRepository().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return repository;
    }

}
