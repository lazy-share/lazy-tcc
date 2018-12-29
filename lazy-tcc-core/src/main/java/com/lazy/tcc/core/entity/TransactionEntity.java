package com.lazy.tcc.core.entity;

import com.lazy.tcc.common.enums.TransactionPhase;
import com.lazy.tcc.common.utils.DateUtils;
import com.lazy.tcc.core.entity.support.BasicEntity;

import java.io.Serializable;

/**
 * <p>
 * TransactionEntity definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class TransactionEntity extends BasicEntity implements Serializable {

    /**
     * Serializable Version
     */
    private static final long serialVersionUID = -994345465462L;

    public TransactionEntity() {

    }

    /**
     * transaction id
     */
    private Long txId;
    /**
     * transaction phase
     * {@link TransactionPhase}
     */
    private TransactionPhase txPhase;
    /**
     * retry count
     */
    private int retryCount;
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

    public String getAppKey() {
        return appKey;
    }

    public TransactionEntity setAppKey(String appKey) {
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

    public TransactionEntity setTxId(Long txId) {
        this.txId = txId;
        return this;
    }

    public TransactionPhase getTxPhase() {
        return txPhase;
    }

    public TransactionEntity setTxPhase(TransactionPhase txPhase) {
        this.txPhase = txPhase;
        return this;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public TransactionEntity setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public TransactionEntity setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public TransactionEntity setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    public long getVersion() {
        return version;
    }

    public TransactionEntity setVersion(long version) {
        this.version = version;
        return this;
    }


    @Override
    public Serializable cacheKey() {
        return this.txId;
    }
}
