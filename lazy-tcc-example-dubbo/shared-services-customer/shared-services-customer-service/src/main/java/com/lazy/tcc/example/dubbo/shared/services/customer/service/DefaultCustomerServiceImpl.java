package com.lazy.tcc.example.dubbo.shared.services.customer.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lazy.tcc.common.enums.ApplicationRole;
import com.lazy.tcc.core.annotation.Compensable;
import com.lazy.tcc.core.annotation.Idempotent;
import com.lazy.tcc.dubbo.propagator.DubboIdempotentContextPropagator;
import com.lazy.tcc.dubbo.propagator.DubboTransactionContextPropagator;
import com.lazy.tcc.example.dubbo.shared.services.customer.ICustomerService;
import com.lazy.tcc.example.dubbo.shared.services.customer.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 * DefaultCustomerServiceImpl
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/11.
 */
@org.springframework.stereotype.Service
@Service(
        version = "${customer.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}",
        proxy = "lazytccjavassist"
)
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class DefaultCustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerRepository repository;


    /**
     * TCC - Try Deduct Capital Api
     *
     * @param customerNo customerNo {@link String}
     * @param capital    capital {@link BigDecimal}
     */
    @Override
    @Compensable(simpleDesc = "扣减客户资金", propagator = DubboTransactionContextPropagator.class, confirmMethod = "confirmDeductCapital", cancelMethod = "cancelDeductCapital")
    public void deductCapital(String customerNo, BigDecimal capital) {
        repository.deductCapital(customerNo, capital);
    }

    /**
     * TCC - Confirm Deduct Capital Api
     *
     * @param customerNo customerNo {@link String}
     * @param capital    capital {@link BigDecimal}
     */
    @Override
    @Idempotent(simpleDesc = "确认扣减客户资金", propagator = DubboIdempotentContextPropagator.class, applicationRole = ApplicationRole.PROVIDER)
    public void confirmDeductCapital(String customerNo, BigDecimal capital) {
        repository.confirmDeductCapital(customerNo, capital);
    }

    /**
     * TCC - Cancel Deduct Capital Api
     *
     * @param customerNo customerNo {@link String}
     * @param capital    capital {@link BigDecimal}
     */
    @Override
    @Idempotent(simpleDesc = "取消扣减客户资金", propagator = DubboIdempotentContextPropagator.class, applicationRole = ApplicationRole.PROVIDER)
    public void cancelDeductCapital(String customerNo, BigDecimal capital) {
        repository.cancelDeductCapital(customerNo, capital);
    }
}
