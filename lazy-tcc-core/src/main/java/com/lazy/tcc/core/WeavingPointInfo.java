package com.lazy.tcc.core;

import com.lazy.tcc.core.annotation.Compensable;
import com.lazy.tcc.core.annotation.Idempotent;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <p>
 * WeavingPointInfo Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public class WeavingPointInfo {


    private final Compensable compensable;
    private final Idempotent idempotent;
    private final ProceedingJoinPoint joinPoint;

    public WeavingPointInfo(Compensable compensable, ProceedingJoinPoint joinPoint) {
        this.compensable = compensable;
        this.joinPoint = joinPoint;
        this.idempotent = null;
    }

    public WeavingPointInfo(Idempotent idempotent, ProceedingJoinPoint joinPoint) {
        this.idempotent = idempotent;
        this.joinPoint = joinPoint;
        this.compensable = null;
    }

    public Idempotent getIdempotent() {
        return idempotent;
    }

    public Compensable getCompensable() {
        return compensable;
    }

    public ProceedingJoinPoint getJoinPoint() {
        return joinPoint;
    }
}
