package com.lazy.tcc.example.dubbo.shared.services.customer;

import com.lazy.tcc.common.enums.ApplicationRole;
import com.lazy.tcc.core.annotation.Compensable;
import com.lazy.tcc.core.annotation.Idempotent;
import com.lazy.tcc.dubbo.propagator.DubboIdempotentContextPropagator;
import com.lazy.tcc.dubbo.propagator.DubboTransactionContextPropagator;

import java.math.BigDecimal;

/**
 * <p>
 * ICustomerService
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/11.
 */
public interface ICustomerService {

    /**
     * TCC - Try Deduct Capital Api
     *
     * @param customerNo customerNo {@link String}
     * @param capital    capital {@link BigDecimal}
     */
    @Compensable(propagator = DubboTransactionContextPropagator.class)
    void deductCapital(String customerNo, BigDecimal capital);


    /**
     * TCC - Confirm Deduct Capital Api
     *
     * @param customerNo customerNo {@link String}
     * @param capital    capital {@link BigDecimal}
     */
    @Idempotent(propagator = DubboIdempotentContextPropagator.class, applicationRole = ApplicationRole.CONSUMER)
    void confirmDeductCapital(String customerNo, BigDecimal capital);


    /**
     * TCC - Cancel Deduct Capital Api
     *
     * @param customerNo customerNo {@link String}
     * @param capital    capital {@link BigDecimal}
     */
    @Idempotent(propagator = DubboIdempotentContextPropagator.class, applicationRole = ApplicationRole.CONSUMER)
    void cancelDeductCapital(String customerNo, BigDecimal capital);

}
