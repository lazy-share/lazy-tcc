package com.lazy.tcc.core.exception;

/**
 * <p>
 * ConnectionIOException Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
public class ConnectionIOException extends RuntimeException {

    public ConnectionIOException() {
    }

    public ConnectionIOException(String message) {
        super(message);
    }

    public ConnectionIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionIOException(Throwable cause) {
        super(cause);
    }

    public ConnectionIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
