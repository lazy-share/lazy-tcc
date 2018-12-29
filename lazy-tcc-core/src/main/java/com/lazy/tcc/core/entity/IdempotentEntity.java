package com.lazy.tcc.core.entity;

import com.lazy.tcc.core.entity.support.BasicEntity;

import java.io.Serializable;

/**
 * <p>
 * IdempotentEntity Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/17.
 */
public class IdempotentEntity extends BasicEntity implements Serializable {

    /**
     * Serializable Version
     */
    private static final long serialVersionUID = 68845234626462L;

    @Override
    public Serializable cacheKey() {
        return this.pk;
    }

    public static class IdempotentPk implements Serializable {

        /**
         * Serializable Version
         */
        private static final long serialVersionUID = 87654345462L;

        private String reqSerialNum;
        private String appKey;

        public String getReqSerialNum() {
            return reqSerialNum;
        }

        public IdempotentPk setReqSerialNum(String reqSerialNum) {
            this.reqSerialNum = reqSerialNum;
            return this;
        }

        public String getAppKey() {
            return appKey;
        }

        public IdempotentPk setAppKey(String appKey) {
            this.appKey = appKey;
            return this;
        }
    }

    private IdempotentPk pk;
    private String businessRec;
    private String createTime;

    public IdempotentPk getPk() {
        return pk;
    }

    public IdempotentEntity setPk(IdempotentPk pk) {
        this.pk = pk;
        return this;
    }

    public String getBusinessRec() {
        return businessRec;
    }

    public IdempotentEntity setBusinessRec(String businessRec) {
        this.businessRec = businessRec;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public IdempotentEntity setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }
}
