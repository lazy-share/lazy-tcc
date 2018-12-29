package com.lazy.tcc.core;

import com.lazy.tcc.common.enums.TransactionPhase;
import com.lazy.tcc.common.utils.DateUtils;
import com.lazy.tcc.common.utils.SnowflakeIdWorkerUtils;
import com.lazy.tcc.core.spi.SpiConfiguration;

import java.io.Serializable;

/**
 * <p>
 * Transaction definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class Transaction implements Serializable {


    /**
     * Serializable Version
     */
    private static final long serialVersionUID = -994345465462L;

    public Transaction() {

    }

    public Transaction init() {
        this.txId = SnowflakeIdWorkerUtils.getSingle().nextId();
        this.txPhase = TransactionPhase.TRY;
        this.retryCount = 0;
        this.appKey = SpiConfiguration.getInstance().getAppKey();
        this.createTime = DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS);
        this.lastUpdateTime = DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS);
        return this;
    }

    /**
     * transaction id
     */
    private Long txId;
    private String appKey;
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

    public void updateVersion() {
        this.version++;
    }

    public void updateLastUpdateTime() {
        this.lastUpdateTime = DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS);
    }

    public String getAppKey() {
        return appKey;
    }

    public Transaction setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public Long getTxId() {
        return txId;
    }

    public Transaction setTxId(Long txId) {
        this.txId = txId;
        return this;
    }

    public TransactionPhase getTxPhase() {
        return txPhase;
    }

    public Transaction setTxPhase(TransactionPhase txPhase) {
        this.txPhase = txPhase;
        return this;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public Transaction setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Transaction setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public Transaction setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    public long getVersion() {
        return version;
    }

    public Transaction setVersion(long version) {
        this.version = version;
        return this;
    }
}
