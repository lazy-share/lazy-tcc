package com.lazy.tcc.core.processor;

import com.lazy.tcc.core.WeavingPointInfo;

/**
 * <p>
 * Processor Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public interface Processor {


    /**
     * processor
     *
     * @param pointInfo {@link WeavingPointInfo}
     * @return {@link Object}
     * @throws Throwable {@link Throwable}
     */
    Object processor(WeavingPointInfo pointInfo) throws Throwable;

}
