package com.lazy.tcc.core.threadpool;


import com.lazy.tcc.common.utils.Assert;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author laizhiyuan
 * @since 2018/1/16.
 * <p>手动创建线程池</p>
 */
public abstract class SysDefaultThreadPool {

    /**
     * CPU密集线程池
     */
    private static ExecutorService cpuCrowdThreadPool;
    /**
     * IO密集线程池
     */
    private static ExecutorService ioCrowdThreadPool;
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(SysDefaultThreadPool.class);

    /**
     * 适用于IO密集型任务线程池
     * 一个项目内不能同时使用cpu密集型和io密集型线程池
     * 线程池行为：
     * 假如CPU核数为4核时，当执行newCpuCrowdThredPool方法后将会创建一个数量为0的线程池；
     * 最多可以创建 4 / (1 - 0.9) 条线程去处理任务，超过的其它任务线程将进入队列排队等待执行
     * 由于队列排队策略使用LinkedBlockingQueue且没有设定队列大小导致maximumPoolSize 和 keepAliveTime配置是无效的
     *
     * @return java.util.concurrent.ExecutorService
     */
    private static ExecutorService newIoCrowdThreadPool() {
        Assert.notNull(cpuCrowdThreadPool, "CPU intensive and IO intensive thread pools cannot be used at the same time in one project");
        int cpuCoreNum = Runtime.getRuntime().availableProcessors();
        logger.info("当前机器CPU核数=" + cpuCoreNum);

        //阻塞系数
        double blockingCoefficient = 0.9;
        //核心线程数量 = CPU核心数量 / (1 - 0.9)
        int corePoolSize = (int) (cpuCoreNum / (1 - blockingCoefficient));
        //最大线程数量(核心线程 + 非核心) = 核心线程数量 + 10
        int maximumPoolSize = corePoolSize + 10;
        //线程没有任务执行时最多保持毫秒
        long keepAliveTime = 200L;

        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadFactoryBuilder().setThreadNamePrefix("pool-ic-").build()
        );
    }


    /**
     * 适用于CPU密集型任务线程池
     * 一个项目内不能同时使用cpu密集型和io密集型线程池
     * 线程池行为：
     * 假如CPU核数为4核时，当执行newCpuCrowdThredPool方法后将会创建一个数量为0的线程池；
     * 最多可以创建 4   条线程去处理任务，超过的其它任务线程将进入队列排队等待执行
     * 由于队列排队策略使用LinkedBlockingQueue且没有设定队列大小导致maximumPoolSize 和 keepAliveTime配置是无效的
     *
     * @return java.util.concurrent.ExecutorService
     */
    private static ExecutorService newCpuCrowdThreadPool() {
        Assert.notNull(ioCrowdThreadPool, "CPU intensive and IO intensive thread pools cannot be used at the same time in one project");
        int cpuCoreNum = Runtime.getRuntime().availableProcessors();
        logger.info("当前机器CPU核数=" + cpuCoreNum);

        //核心线程数量 = CPU核心数量
        int corePoolSize = cpuCoreNum;
        //最大线程数量 = 核心线程数量 + 10
        int maximumPoolSize = corePoolSize + 1;
        //线程没有任务执行时最多保持毫秒
        long keepAliveTime = 200L;

        return new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadFactoryBuilder().setThreadNamePrefix("pool-cc-").build()
        );
    }

    /**
     * 执行CPU 密集型线程任务
     *
     * @param task 实现Runnable线程任务
     */
    public static void executeCpuCrowdThreadTask(Runnable task) {
        if (cpuCrowdThreadPool == null) {
            cpuCrowdThreadPool = newCpuCrowdThreadPool();
        }
        cpuCrowdThreadPool.execute(task);
    }

    /**
     * 执行IO 密集型线程任务
     *
     * @param task 实现Runnable线程任务
     */
    public static void executeIoCrowdThreadTask(Runnable task) {
        if (ioCrowdThreadPool == null) {
            ioCrowdThreadPool = newIoCrowdThreadPool();
        }
        ioCrowdThreadPool.execute(task);
    }
}
