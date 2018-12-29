package com.lazy.tcc.core.repository;

import com.lazy.tcc.core.repository.support.AbstractParticipantRepository;
import com.lazy.tcc.core.spi.SpiConfiguration;

/**
 * <p>
 * TransactionRepositoryFactory Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public final class ParticipantRepositoryFactory {


    private ParticipantRepositoryFactory() {
    }

    private static volatile AbstractParticipantRepository repository;

    public static AbstractParticipantRepository create() {
        if (repository == null) {
            synchronized (ParticipantRepositoryFactory.class) {
                if (repository == null) {
                    try {
                        repository = SpiConfiguration.getInstance().getParticipantRepository().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return repository;
    }

}
