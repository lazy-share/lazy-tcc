package com.lazy.tcc.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.lazy.tcc.core.WeavingPointInfo;
import com.lazy.tcc.core.annotation.Idempotent;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.processor.IdempotentProcessor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * <p>
 * IdempotentInterceptor Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/17.
 */
@Aspect
public class IdempotentInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionInterceptor.class);

    /**
     * processor
     */
    private final IdempotentProcessor processor = IdempotentProcessor.getSingle();

    /**
     * interceptor specify compensable transaction annotation
     * When the annotated Compensable is found in the method, it will be intercepted.
     *
     * @param joinPoint  JoinPoint {@link ProceedingJoinPoint}
     * @param idempotent Compensable {@link Idempotent}
     * @return {@link Object}
     * @throws Throwable {@link Throwable}
     */
    @Around("@annotation(idempotent)")
    public Object around(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("idempotent weaving point info: " + JSON.toJSONString(joinPoint));
        }

        //idempotent processor
        return processor.processor(new WeavingPointInfo(idempotent, joinPoint));
    }
}
