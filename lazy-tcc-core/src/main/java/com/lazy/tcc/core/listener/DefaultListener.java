package com.lazy.tcc.core.listener;

import com.lazy.tcc.common.utils.StringUtils;
import com.lazy.tcc.core.exception.SystemException;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.repository.IdempotentRepositoryFactory;
import com.lazy.tcc.core.repository.ParticipantRepositoryFactory;
import com.lazy.tcc.core.repository.TransactionRepositoryFactory;
import com.lazy.tcc.core.scheduler.CompensableTransactionScheduler;
import com.lazy.tcc.core.spi.SpiConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * <p>
 * DefaultListener Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/19.
 */
public class DefaultListener implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultListener.class);


    private ApplicationContext context;
    private DataSource applicationDataSource;
    private DataSource transactionDataSource;

    private DataSource createDataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        try {
            dataSource.setDriverClass(SpiConfiguration.getInstance().getDatasourceDriver());
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        dataSource.setJdbcUrl(SpiConfiguration.getInstance().getDatasourceUrl());
        dataSource.setUser(SpiConfiguration.getInstance().getDatasourceUsername());
        dataSource.setPassword(SpiConfiguration.getInstance().getDatasourcePassword());
        dataSource.setAutoCommitOnClose(true);


        return dataSource;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {

            checkedConfigure();
            checkDataSource();

            LOGGER.info("init transactionDataSource and idempotentDataSource");
            TransactionRepositoryFactory.create().setDataSource(transactionDataSource).createTable();
            IdempotentRepositoryFactory.create().setDataSource(applicationDataSource).createTable();
            ParticipantRepositoryFactory.create().setDataSource(transactionDataSource).createTable();

            if (SpiConfiguration.getInstance().isEnableCompensableScheduler()) {
                new CompensableTransactionScheduler().init();
            }
        }
    }

    private void checkDataSource() {
        if (applicationDataSource == null) {
            applicationDataSource = context.getBean(DataSource.class);
        }
        if (transactionDataSource == null) {
            transactionDataSource = createDataSource();
        }
        if (applicationDataSource == null) {
            throw new SystemException("applicationDataSource is null");
        }
        if (transactionDataSource == null) {
            throw new SystemException("transactionDataSource is null");
        }
    }

    private void checkedConfigure() {
        if (StringUtils.isBlank(SpiConfiguration.getInstance().getTxDatabaseName())) {
            throw new SystemException("please autoconfig lazy tcc getTxDatabaseName");
        }
        if (StringUtils.isBlank(SpiConfiguration.getInstance().getApplicationDatabaseName())) {
            throw new SystemException("please autoconfig lazy tcc getApplicationDatabaseName");
        }
        if (StringUtils.isBlank(SpiConfiguration.getInstance().getTxTableName())) {
            throw new SystemException("please autoconfig lazy tcc getTxTableName");
        }
        if (StringUtils.isBlank(SpiConfiguration.getInstance().getParticipantTableName())) {
            throw new SystemException("please autoconfig lazy tcc getParticipantTableName");
        }
        if (StringUtils.isBlank(SpiConfiguration.getInstance().getAppKeyTableName())) {
            throw new SystemException("please autoconfig lazy tcc getAppKeyTableName");
        }
        if (StringUtils.isBlank(SpiConfiguration.getInstance().getAppKey())) {
            throw new SystemException("please autoconfig lazy tcc getAppKey");
        }
        if (StringUtils.isBlank(SpiConfiguration.getInstance().getIdempotentTableName())) {
            throw new SystemException("please autoconfig lazy tcc getIdempotentTableName");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public DataSource getApplicationDataSource() {
        return applicationDataSource;
    }

    public DefaultListener setApplicationDataSource(DataSource applicationDataSource) {
        this.applicationDataSource = applicationDataSource;
        return this;
    }

    public DataSource getTransactionDataSource() {
        return transactionDataSource;
    }

    public DefaultListener setTransactionDataSource(DataSource transactionDataSource) {
        this.transactionDataSource = transactionDataSource;
        return this;
    }
}
