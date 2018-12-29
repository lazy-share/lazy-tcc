package com.lazy.tcc.core.exception;

/**
 * <p>
 * ConfirmException Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class ConfirmException extends RuntimeException {

    static final long serialVersionUID = -3546575439L;

    public ConfirmException() {
    }

    public ConfirmException(String message) {
        super(message);
    }

    public ConfirmException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfirmException(Throwable cause) {
        super(cause);
    }

    public ConfirmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
