package com.lazy.tcc.core;

import java.io.Serializable;

/**
 * <p>
 * Idempotent Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/17.
 */
public class Idempotent implements Serializable {

    /**
     * Serializable Version
     */
    private static final long serialVersionUID = 68845234626462L;

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

    public Idempotent setPk(IdempotentPk pk) {
        this.pk = pk;
        return this;
    }

    public String getBusinessRec() {
        return businessRec;
    }

    public Idempotent setBusinessRec(String businessRec) {
        this.businessRec = businessRec;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Idempotent setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }
}
