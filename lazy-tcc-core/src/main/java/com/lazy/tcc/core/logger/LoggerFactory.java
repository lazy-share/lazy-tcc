package com.lazy.tcc.core.logger;


import com.lazy.tcc.core.logger.jcl.JclLoggerAdapter;
import com.lazy.tcc.core.logger.jdk.JdkLoggerAdapter;
import com.lazy.tcc.core.logger.log4j.Log4jLoggerAdapter;
import com.lazy.tcc.core.logger.slf4j.Slf4jLoggerAdapter;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Logger 工厂
 *
 * @author laizhiyuan
 * @since 2018/6/28.
 */
public class LoggerFactory {

    private static final ConcurrentMap<String, LoggerFqcn> LOGGERS = new ConcurrentHashMap<String, LoggerFqcn>();
    private static volatile LoggerAdapter LOGGER_ADAPTER;

    static {
        try {
            setLoggerAdapter(new Slf4jLoggerAdapter());
        } catch (Throwable e1) {
            try {
                setLoggerAdapter(new Log4jLoggerAdapter());
            } catch (Throwable e2) {
                try {
                    setLoggerAdapter(new JclLoggerAdapter());
                } catch (Throwable e3) {
                    try {
                        setLoggerAdapter(new JdkLoggerAdapter());
                    } catch (Throwable ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }

    }

    private LoggerFactory() {
    }


    /**
     * Set logger provider
     *
     * @param loggerAdapter logger provider
     */
    public static void setLoggerAdapter(LoggerAdapter loggerAdapter) {
        if (loggerAdapter != null) {
            Logger logger = loggerAdapter.getLogger(LoggerFactory.class.getName());
            logger.info("using logger: " + loggerAdapter.getClass().getName());
            LoggerFactory.LOGGER_ADAPTER = loggerAdapter;
            for (Map.Entry<String, LoggerFqcn> entry : LOGGERS.entrySet()) {
                entry.getValue().setLogger(LOGGER_ADAPTER.getLogger(entry.getKey()));
            }
        }
    }

    /**
     * Get logger provider
     *
     * @param key the returned logger will be named after clazz
     * @return logger
     */
    public static Logger getLogger(Class<?> key) {
        LoggerFqcn logger = LOGGERS.get(key.getName());
        if (logger == null) {
            LOGGERS.putIfAbsent(key.getName(), new LoggerFqcn(LOGGER_ADAPTER.getLogger(key)));
            logger = LOGGERS.get(key.getName());
        }
        return logger;
    }

    /**
     * Get logger provider
     *
     * @param key the returned logger will be named after key
     * @return logger provider
     */
    public static Logger getLogger(String key) {
        LoggerFqcn logger = LOGGERS.get(key);
        if (logger == null) {
            LOGGERS.putIfAbsent(key, new LoggerFqcn(LOGGER_ADAPTER.getLogger(key)));
            logger = LOGGERS.get(key);
        }
        return logger;
    }

    /**
     * Get logging level
     *
     * @return logging level
     */
    public static Level getLevel() {
        return LOGGER_ADAPTER.getLevel();
    }

    /**
     * Set the current logging level
     *
     * @param level logging level
     */
    public static void setLevel(Level level) {
        LOGGER_ADAPTER.setLevel(level);
    }

    /**
     * Get the current logging file
     *
     * @return current logging file
     */
    public static File getFile() {
        return LOGGER_ADAPTER.getFile();
    }

}