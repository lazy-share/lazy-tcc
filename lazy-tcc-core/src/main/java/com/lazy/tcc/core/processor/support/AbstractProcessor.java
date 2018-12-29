package com.lazy.tcc.core.processor.support;

import com.lazy.tcc.core.TransactionManager;
import com.lazy.tcc.core.WeavingPointInfo;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.processor.Processor;

/**
 * <p>
 * AbstractProcessor Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public abstract class AbstractProcessor implements Processor {

    /**
     * transactionManager
     */
    protected final TransactionManager transactionManager = TransactionManager.getSingle();

    /**
     * logger
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * abstract method, Implemented by subclasses
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @return {@link Boolean}
     */
    protected abstract boolean isAccept(WeavingPointInfo pointInfo);

    /**
     * abstract method, Implemented by subclasses
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @throws Throwable {@link Throwable}
     */
    protected abstract Object doProcessor(WeavingPointInfo pointInfo) throws Throwable;

    /**
     * Decide to continue execution if accessible
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @return {@link Object}
     * @throws Throwable
     */
    @Override
    public Object processor(WeavingPointInfo pointInfo) throws Throwable {

        //if can accept
        if (isAccept(pointInfo)) {

            //continue execute
            return this.doProcessor(pointInfo);
        }

        //else execute interceptor method
        return pointInfo.getJoinPoint().proceed();
    }

}
