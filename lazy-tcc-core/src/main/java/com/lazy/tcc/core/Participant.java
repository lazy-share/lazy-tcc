package com.lazy.tcc.core;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * TransactionEntity participants
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
@EqualsAndHashCode
public class Participant implements Serializable {

    /**
     * Serializable Version
     */
    private static final long serialVersionUID = 4565437665462L;

    private Long id;
    private Long txId;
    private String appKey;
    /**
     * transaction last update time
     */
    private String lastUpdateTime;
    /**
     * transaction create time
     */
    private String createTime;
    /**
     * optimistic version
     */
    private long version = 1;
    private Invoker confirmMethodInvoker;
    private Invoker cancelMethodInvoker;

    public Long getId() {
        return id;
    }

    public Participant setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Participant setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public Participant setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    public long getVersion() {
        return version;
    }

    public Participant setVersion(long version) {
        this.version = version;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    public Participant setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public Long getTxId() {
        return txId;
    }


    public Participant setTxId(Long txId) {
        this.txId = txId;
        return this;
    }

    public Invoker getConfirmMethodInvoker() {
        return confirmMethodInvoker;
    }

    public Participant setConfirmMethodInvoker(Invoker confirmMethodInvoker) {
        this.confirmMethodInvoker = confirmMethodInvoker;
        return this;
    }

    public Invoker getCancelMethodInvoker() {
        return cancelMethodInvoker;
    }

    public Participant setCancelMethodInvoker(Invoker cancelMethodInvoker) {
        this.cancelMethodInvoker = cancelMethodInvoker;
        return this;
    }

    void confirm(TransactionContext context) {
        confirmMethodInvoker.invoker(context);
    }

    void cancel(TransactionContext context) {
        cancelMethodInvoker.invoker(context);
    }


}
