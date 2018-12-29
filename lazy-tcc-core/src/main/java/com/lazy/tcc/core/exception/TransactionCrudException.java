package com.lazy.tcc.core.exception;

/**
 * <p>
 *     TransactionCrudException Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
public class TransactionCrudException extends RuntimeException {

    static final long serialVersionUID = -799996456456L;

    public TransactionCrudException() {
    }

    public TransactionCrudException(String message) {
        super(message);
    }

    public TransactionCrudException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionCrudException(Throwable cause) {
        super(cause);
    }

    public TransactionCrudException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
