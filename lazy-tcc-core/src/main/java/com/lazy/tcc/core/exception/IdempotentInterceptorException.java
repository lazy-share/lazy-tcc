package com.lazy.tcc.core.exception;

/**
 * <p>
 *     IdempotentInterceptorException Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public class IdempotentInterceptorException extends RuntimeException {

    private static final long serialVersionUID = -5590745766939L;

    public IdempotentInterceptorException() {
    }

    public IdempotentInterceptorException(String message) {
        super(message);
    }

    public IdempotentInterceptorException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdempotentInterceptorException(Throwable cause) {
        super(cause);
    }

    public IdempotentInterceptorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
