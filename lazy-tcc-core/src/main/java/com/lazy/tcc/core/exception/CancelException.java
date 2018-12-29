package com.lazy.tcc.core.exception;

/**
 * <p>
 * CancelException Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class CancelException extends RuntimeException {

    static final long serialVersionUID = -985784645766939L;

    public CancelException() {
    }

    public CancelException(String message) {
        super(message);
    }

    public CancelException(String message, Throwable cause) {
        super(message, cause);
    }

    public CancelException(Throwable cause) {
        super(cause);
    }

    public CancelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
