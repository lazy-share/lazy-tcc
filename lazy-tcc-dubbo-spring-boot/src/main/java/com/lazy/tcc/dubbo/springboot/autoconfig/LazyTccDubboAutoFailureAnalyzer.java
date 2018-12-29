package com.lazy.tcc.dubbo.springboot.autoconfig;


import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * <p>
 * 启动错误的自定义报告。
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/11/17.
 */
public class LazyTccDubboAutoFailureAnalyzer extends AbstractFailureAnalyzer<Exception> {

    private static Logger logger = LoggerFactory.getLogger(LazyTccDubboAutoFailureAnalyzer.class);

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, Exception cause) {
        logger.error(rootFailure);
        logger.error(cause);
        throw new RuntimeException(cause);
    }
}
