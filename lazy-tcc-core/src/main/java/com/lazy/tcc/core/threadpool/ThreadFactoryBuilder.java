package com.lazy.tcc.core.threadpool;

import java.util.concurrent.ThreadFactory;

/**
 * @author laizhiyuan
 * @since 2018/1/16.
 * <p>线程工厂构建者</p>
 */
public class ThreadFactoryBuilder {

    private String threadNamePrefix;

    public ThreadFactoryBuilder setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
        return this;
    }

    public ThreadFactory build() {
        if (threadNamePrefix == null) {
            threadNamePrefix = "pool-";
        }
        return new SystemDefaultThreadFactory(this.threadNamePrefix);
    }
}
