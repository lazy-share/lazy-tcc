package com.lazy.tcc.core.exception;

/**
 * <p>
 * TransactionManagerException Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public class TransactionManagerException extends RuntimeException {

    static final long serialVersionUID = -6598536258L;

    public TransactionManagerException() {
    }

    public TransactionManagerException(String message) {
        super(message);
    }

    public TransactionManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionManagerException(Throwable cause) {
        super(cause);
    }

    public TransactionManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
