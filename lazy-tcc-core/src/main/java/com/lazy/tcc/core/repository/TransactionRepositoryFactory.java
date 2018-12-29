package com.lazy.tcc.core.repository;

import com.lazy.tcc.core.repository.support.AbstractTransactionRepository;
import com.lazy.tcc.core.spi.SpiConfiguration;

/**
 * <p>
 * TransactionRepositoryFactory Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public final class TransactionRepositoryFactory {


    private TransactionRepositoryFactory() {
    }

    private static volatile AbstractTransactionRepository repository;

    public static AbstractTransactionRepository create() {
        if (repository == null) {
            synchronized (TransactionRepositoryFactory.class) {
                if (repository == null) {
                    try {
                        repository = SpiConfiguration.getInstance().getTxRepository().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return repository;
    }

}
