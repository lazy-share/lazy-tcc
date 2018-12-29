package com.lazy.tcc.core;

import com.lazy.tcc.common.enums.TransactionPhase;

/**
 * <p>
 * TransactionContext Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class TransactionContext {

    /**
     * Serializable Version
     */
    private static final long serialVersionUID = 8764437542L;

    private Long txId;
    private TransactionPhase txPhase;

    public Long getTxId() {
        return txId;
    }

    public TransactionContext setTxId(Long txId) {
        this.txId = txId;
        return this;
    }

    public TransactionPhase getTxPhase() {
        return txPhase;
    }

    public TransactionContext setTxPhase(TransactionPhase txPhase) {
        this.txPhase = txPhase;
        return this;
    }
}
