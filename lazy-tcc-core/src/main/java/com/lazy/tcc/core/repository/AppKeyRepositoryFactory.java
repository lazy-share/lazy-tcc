package com.lazy.tcc.core.repository;

import com.lazy.tcc.core.repository.support.AbstractAppKeyRepository;
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
public final class AppKeyRepositoryFactory {


    private AppKeyRepositoryFactory() {
    }

    private static volatile AbstractAppKeyRepository repository;

    public static AbstractAppKeyRepository create() {
        if (repository == null) {
            synchronized (AppKeyRepositoryFactory.class) {
                if (repository == null) {
                    try {
                        repository = SpiConfiguration.getInstance().getAppkeyRepository().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return repository;
    }

}
