package com.lazy.tcc.core.entity.support;

import java.io.Serializable;

/**
 * <p>
 * BasicEntity Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/17.
 */
public abstract class BasicEntity implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 345678654345243L;

    public abstract Serializable cacheKey();

}
