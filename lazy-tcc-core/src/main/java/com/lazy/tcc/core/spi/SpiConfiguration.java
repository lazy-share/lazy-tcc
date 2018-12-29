package com.lazy.tcc.core.spi;

import com.lazy.tcc.common.utils.ReflectionUtils;
import com.lazy.tcc.common.utils.StringUtils;
import com.lazy.tcc.core.cache.Cache;
import com.lazy.tcc.core.cache.guava.GoogleGuavaCache;
import com.lazy.tcc.core.logger.LoggerAdapter;
import com.lazy.tcc.core.logger.slf4j.Slf4jLoggerAdapter;
import com.lazy.tcc.core.repository.jdbc.MysqlAppKeyRepository;
import com.lazy.tcc.core.repository.jdbc.MysqlIdempotentRepository;
import com.lazy.tcc.core.repository.jdbc.MysqlParticipantRepository;
import com.lazy.tcc.core.repository.jdbc.MysqlTransactionRepository;
import com.lazy.tcc.core.repository.support.AbstractAppKeyRepository;
import com.lazy.tcc.core.repository.support.AbstractIdempotentRepository;
import com.lazy.tcc.core.repository.support.AbstractParticipantRepository;
import com.lazy.tcc.core.repository.support.AbstractTransactionRepository;
import com.lazy.tcc.core.serializer.Serialization;
import com.lazy.tcc.core.serializer.jdk.JdkSerialization;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>
 * Spi Config
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/10/16.
 */
@ConfigurationProperties("lazy.tcc.config")
public class SpiConfiguration {

    private Class<? extends Serialization> seriClassImpl = JdkSerialization.class;
    private Class<? extends Cache> cacheClassImpl = GoogleGuavaCache.class;
    private Class<? extends AbstractTransactionRepository> txRepository = MysqlTransactionRepository.class;
    private Class<? extends AbstractIdempotentRepository> idempotentRepository = MysqlIdempotentRepository.class;
    private Class<? extends AbstractAppKeyRepository> appkeyRepository = MysqlAppKeyRepository.class;
    private Class<? extends AbstractParticipantRepository> participantRepository = MysqlParticipantRepository.class;
    private String txTableName = "lazy_tcc_transaction";
    private String txDatabaseName;
    private String idempotentTableName = "lazy_tcc_idempotent";
    private String appKeyTableName = "lazy_tcc_app_key";
    private String participantTableName = "lazy_tcc_participant";
    private String applicationDatabaseName;
    private String appKey;
    private String appDesc;
    private int retryCount = 5;
    private int keepRequestLogDayCount = 31;
    private int compensationMinuteInterval = 10;
    private boolean enableCompensableScheduler = true;


    public Class<? extends AbstractAppKeyRepository> getAppkeyRepository() {
        return appkeyRepository;
    }

    public SpiConfiguration setAppkeyRepository(Class<? extends AbstractAppKeyRepository> appkeyRepository) {
        this.appkeyRepository = appkeyRepository;
        return this;
    }

    public Class<? extends AbstractParticipantRepository> getParticipantRepository() {
        return participantRepository;
    }

    public SpiConfiguration setParticipantRepository(Class<? extends AbstractParticipantRepository> participantRepository) {
        this.participantRepository = participantRepository;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    public SpiConfiguration setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public SpiConfiguration setAppDesc(String appDesc) {
        this.appDesc = appDesc;
        return this;
    }

    public String getAppKeyTableName() {
        return appKeyTableName;
    }

    public SpiConfiguration setAppKeyTableName(String appKeyTableName) {
        this.appKeyTableName = appKeyTableName;
        return this;
    }

    public String getParticipantTableName() {
        return participantTableName;
    }

    public SpiConfiguration setParticipantTableName(String participantTableName) {
        this.participantTableName = participantTableName;
        return this;
    }

    public boolean isEnableCompensableScheduler() {
        return enableCompensableScheduler;
    }

    public SpiConfiguration setEnableCompensableScheduler(boolean enableCompensableScheduler) {
        this.enableCompensableScheduler = enableCompensableScheduler;
        return this;
    }

    private String datasourceDriver;
    private String datasourceUrl;
    private String datasourceUsername;
    private String datasourcePassword;

    public String getTxDatabaseName() {
        return txDatabaseName;
    }

    public SpiConfiguration setTxDatabaseName(String txDatabaseName) {
        this.txDatabaseName = txDatabaseName;
        return this;
    }

    public String getApplicationDatabaseName() {
        return applicationDatabaseName;
    }

    public SpiConfiguration setApplicationDatabaseName(String applicationDatabaseName) {
        this.applicationDatabaseName = applicationDatabaseName;
        return this;
    }

    public String getDatasourceDriver() {
        return datasourceDriver;
    }

    public SpiConfiguration setDatasourceDriver(String datasourceDriver) {
        this.datasourceDriver = datasourceDriver;
        return this;
    }

    public String getDatasourceUrl() {
        return datasourceUrl;
    }

    public SpiConfiguration setDatasourceUrl(String datasourceUrl) {
        this.datasourceUrl = datasourceUrl;
        return this;
    }

    public String getDatasourceUsername() {
        return datasourceUsername;
    }

    public SpiConfiguration setDatasourceUsername(String datasourceUsername) {
        this.datasourceUsername = datasourceUsername;
        return this;
    }

    public String getDatasourcePassword() {
        return datasourcePassword;
    }

    public SpiConfiguration setDatasourcePassword(String datasourcePassword) {
        this.datasourcePassword = datasourcePassword;
        return this;
    }

    private static String defaultAppKey() {
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String ip = addr.getHostAddress();
        String hostName = addr.getHostName();

        return String.format("%s_%s", hostName, ip);
    }


    public int getKeepRequestLogDayCount() {
        return keepRequestLogDayCount;
    }

    public SpiConfiguration setKeepRequestLogDayCount(int keepRequestLogDayCount) {
        this.keepRequestLogDayCount = keepRequestLogDayCount;
        return this;
    }

    public int getCompensationMinuteInterval() {
        return compensationMinuteInterval;
    }

    public SpiConfiguration setCompensationMinuteInterval(int compensationMinuteInterval) {
        this.compensationMinuteInterval = compensationMinuteInterval;
        return this;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public SpiConfiguration setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public Class<? extends AbstractIdempotentRepository> getIdempotentRepository() {
        return idempotentRepository;
    }

    public SpiConfiguration setIdempotentRepository(Class<? extends AbstractIdempotentRepository> idempotentRepository) {
        this.idempotentRepository = idempotentRepository;
        return this;
    }

    public String getIdempotentTableName() {
        return idempotentTableName;
    }

    public SpiConfiguration setIdempotentTableName(String idempotentTableName) {
        this.idempotentTableName = idempotentTableName;
        return this;
    }

    public String getTxTableName() {
        return txTableName;
    }

    public SpiConfiguration setTxTableName(String txTableName) {
        this.txTableName = txTableName;
        return this;
    }

    public Class<? extends AbstractTransactionRepository> getTxRepository() {
        return txRepository;
    }

    public SpiConfiguration setTxRepository(Class<? extends AbstractTransactionRepository> txRepository) {
        this.txRepository = txRepository;
        return this;
    }

    public Class<? extends Serialization> getSeriClassImpl() {
        return seriClassImpl;
    }

    public SpiConfiguration setSeriClassImpl(Class<? extends Serialization> seriClassImpl) {
        this.seriClassImpl = seriClassImpl;
        return this;
    }

    public Class<? extends Cache> getCacheClassImpl() {
        return cacheClassImpl;
    }

    public SpiConfiguration setCacheClassImpl(Class<? extends Cache> cacheClassImpl) {
        this.cacheClassImpl = cacheClassImpl;
        return this;
    }

    /**
     * 单例对象
     */
    private static SpiConfiguration singleton;

    /**
     * Config Pre
     */
    private static final String ROOT_PRE = "lazy.tcc.config.";


    /**
     * 获取单例对象
     *
     * @return
     */
    public static SpiConfiguration getInstance() {
        if (singleton == null) {
            synchronized (SpiConfiguration.class) {
                if (singleton == null) {
                    singleton = new SpiConfiguration();
                    Class clazz = singleton.getClass();
                    Class type;
                    String configName;
                    String configVal;
                    Field[] fields = clazz.getDeclaredFields();
                    try {
                        for (Field field : fields) {
                            if (field.getName().equalsIgnoreCase("ROOT_PRE")
                                    || field.getName().equalsIgnoreCase("singleton")) {
                                continue;
                            }
                            type = field.getType();
                            configName = ROOT_PRE + StringUtils.toUnderline(field.getName()).replaceAll("_", "-");
                            configVal = PropertiesReader.getInstance().getProp(configName).getVal();
                            if (configVal == null) {
                                continue;
                            }
                            if (type.equals(Class.class)) {
                                ReflectionUtils.setFieldValue(field.getName(), Class.forName(configVal), singleton);
                            } else if (type.equals(Integer.class) || type.equals(int.class)) {
                                ReflectionUtils.setFieldValue(field.getName(), Integer.valueOf(configVal), singleton);
                            } else if (type.equals(Long.class) || type.equals(long.class)) {
                                ReflectionUtils.setFieldValue(field.getName(), Long.valueOf(configVal), singleton);
                            } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                                ReflectionUtils.setFieldValue(field.getName(), Boolean.valueOf(configVal), singleton);
                            } else {
                                ReflectionUtils.setFieldValue(field.getName(), configVal, singleton);
                            }

                        }
                    } catch (Exception ex) {

                        throw new RuntimeException("init PropertiesConfiguration ERROR", ex);
                    }
                }
            }
        }
        return singleton;
    }


}
