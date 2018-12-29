package com.lazy.tcc.example.dubbo.shared.services.stock.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lazy.tcc.common.enums.ApplicationRole;
import com.lazy.tcc.core.annotation.Compensable;
import com.lazy.tcc.core.annotation.Idempotent;
import com.lazy.tcc.dubbo.propagator.DubboIdempotentContextPropagator;
import com.lazy.tcc.dubbo.propagator.DubboTransactionContextPropagator;
import com.lazy.tcc.example.dubbo.shared.services.stock.api.IStockService;
import com.lazy.tcc.example.dubbo.shared.services.stock.api.dto.SimpleResponseBuilder;
import com.lazy.tcc.example.dubbo.shared.services.stock.api.dto.SimpleResponseDto;
import com.lazy.tcc.example.dubbo.shared.services.stock.api.dto.StockEditorDto;
import com.lazy.tcc.example.dubbo.shared.services.stock.repository.IStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

;

/**
 * <p>
 * Definition A Default Stock Service Impl
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/10.
 */
@org.springframework.stereotype.Service
@Service(
        version = "${stock.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}",
        proxy = "lazytccjavassist"
)
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class DefaultStockServiceImpl implements IStockService {

    @Autowired
    private IStockRepository repository;

    /**
     * TCC - Try Deduct Stock Api
     *
     * @param dto param {@link StockEditorDto}
     * @return Operation Result {@link SimpleResponseDto } {@link String}
     */
    @Override
    @Compensable(simpleDesc = "扣减库存", confirmMethod = "confirmDeductStock", cancelMethod = "cancelDeductStock", propagator = DubboTransactionContextPropagator.class)
    public SimpleResponseDto<String> deductStock(StockEditorDto dto) {
        repository.deductStock(dto.getProductSku(), dto.getStockNum());

        return SimpleResponseBuilder.success("ok");
    }

    /**
     * TCC - Confirm Deduct Stock Api
     *
     * @param dto param {@link StockEditorDto}
     * @return Operation Result {@link SimpleResponseDto } {@link String}
     */
    @Override
    @Idempotent(simpleDesc = "确认扣减库存请求", applicationRole = ApplicationRole.PROVIDER, propagator = DubboIdempotentContextPropagator.class)
    public SimpleResponseDto<String> confirmDeductStock(StockEditorDto dto) {
        repository.confirmDeductStock(dto.getProductSku(), dto.getStockNum());

        return SimpleResponseBuilder.success("ok");
    }

    /**
     * TCC - Cancel Deduct Stock Api
     *
     * @param dto param {@link StockEditorDto}
     * @return Operation Result {@link SimpleResponseDto } {@link String}
     */
    @Override
    @Idempotent(simpleDesc = "取消扣减库存请求", applicationRole = ApplicationRole.PROVIDER, propagator = DubboIdempotentContextPropagator.class)
    public SimpleResponseDto<String> cancelDeductStock(StockEditorDto dto) {
        repository.cancelDeductStock(dto.getProductSku(), dto.getStockNum());

        return SimpleResponseBuilder.success("ok");
    }
}
