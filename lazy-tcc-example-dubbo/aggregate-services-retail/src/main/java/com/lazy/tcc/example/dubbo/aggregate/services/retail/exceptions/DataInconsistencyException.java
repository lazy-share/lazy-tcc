package com.lazy.tcc.example.dubbo.aggregate.services.retail.exceptions;

/**
 * <p>
 * DataInconsistencyException
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/11.
 */
public class DataInconsistencyException extends RuntimeException{

    static final long serialVersionUID = -9034897190745766939L;

    public DataInconsistencyException() {
    }

    public DataInconsistencyException(String message) {
        super(message);
    }

    public DataInconsistencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataInconsistencyException(Throwable cause) {
        super(cause);
    }

    public DataInconsistencyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
