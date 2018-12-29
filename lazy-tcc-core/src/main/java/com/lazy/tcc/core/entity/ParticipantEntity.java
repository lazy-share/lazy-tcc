package com.lazy.tcc.core.entity;

import com.lazy.tcc.common.utils.DateUtils;
import com.lazy.tcc.core.Invoker;
import com.lazy.tcc.core.entity.support.BasicEntity;

import java.io.Serializable;

/**
 * <p>
 * ParticipantEntity definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class ParticipantEntity extends BasicEntity implements Serializable {

    /**
     * Serializable Version
     */
    private static final long serialVersionUID = -66666735465462L;

    public ParticipantEntity() {

    }

    private Long id;

    /**
     * transaction id
     */
    private Long txId;
    /**
     * transaction create time
     */
    private String createTime;
    /**
     * transaction last update time
     */
    private String lastUpdateTime;
    /**
     * optimistic version
     */
    private long version = 1;

    private String appKey;
    private Invoker confirmMethodInvoker;
    private Invoker cancelMethodInvoker;

    public Invoker getConfirmMethodInvoker() {
        return confirmMethodInvoker;
    }

    public ParticipantEntity setConfirmMethodInvoker(Invoker confirmMethodInvoker) {
        this.confirmMethodInvoker = confirmMethodInvoker;
        return this;
    }

    public Invoker getCancelMethodInvoker() {
        return cancelMethodInvoker;
    }

    public ParticipantEntity setCancelMethodInvoker(Invoker cancelMethodInvoker) {
        this.cancelMethodInvoker = cancelMethodInvoker;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    public ParticipantEntity setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public void updateVersion() {
        this.version++;
    }

    public void updateLastUpdateTime() {
        this.lastUpdateTime = DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS);
    }

    public Long getTxId() {
        return txId;
    }

    public ParticipantEntity setTxId(Long txId) {
        this.txId = txId;
        return this;
    }


    public String getCreateTime() {
        return createTime;
    }

    public ParticipantEntity setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public ParticipantEntity setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    public long getVersion() {
        return version;
    }

    public ParticipantEntity setVersion(long version) {
        this.version = version;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ParticipantEntity setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Serializable cacheKey() {
        return this.id;
    }
}
