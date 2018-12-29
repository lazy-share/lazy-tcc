package com.lazy.tcc.core.entity;

import com.lazy.tcc.core.entity.support.BasicEntity;

import java.io.Serializable;

/**
 * <p>
 * AppKeyEntity definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class AppKeyEntity extends BasicEntity implements Serializable {

    /**
     * Serializable Version
     */
    private static final long serialVersionUID = -78546994345465462L;

    public AppKeyEntity() {

    }


    private String createTime;
    private String appKey;
    private String appDesc;

    public String getAppDesc() {
        return appDesc;
    }

    public AppKeyEntity setAppDesc(String appDesc) {
        this.appDesc = appDesc;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    public AppKeyEntity setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }


    public String getCreateTime() {
        return createTime;
    }

    public AppKeyEntity setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }


    @Override
    public Serializable cacheKey() {
        return this.appKey;
    }
}
