package com.lazy.tcc.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.lazy.tcc.core.WeavingPointInfo;
import com.lazy.tcc.core.annotation.Compensable;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.processor.TransactionProcessor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * <p>
 * CompensableTransactionInterceptro Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
@Aspect
public class TransactionInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionInterceptor.class);

    /**
     * processor
     */
    private final TransactionProcessor processor = TransactionProcessor.getSingle();

    /**
     * interceptor specify compensable transaction annotation
     * When the annotated Compensable is found in the method, it will be intercepted.
     *
     * @param joinPoint   JoinPoint {@link ProceedingJoinPoint}
     * @param compensable Compensable {@link Compensable}
     * @return {@link Object}
     * @throws Throwable {@link Throwable}
     */
    @Around("@annotation(compensable)")
    public Object around(ProceedingJoinPoint joinPoint, Compensable compensable) throws Throwable {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("weaving point method: [%s], args: [%s], compensable datasource info: [%s]"
                    , joinPoint.getSignature(), JSON.toJSONString(joinPoint.getArgs()), JSON.toJSONString(compensable)));
        }

        //try phase processor
        return processor.processor(new WeavingPointInfo(compensable, joinPoint));
    }

}
