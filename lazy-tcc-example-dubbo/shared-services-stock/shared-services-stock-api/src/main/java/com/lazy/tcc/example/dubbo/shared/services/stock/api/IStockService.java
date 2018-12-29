package com.lazy.tcc.example.dubbo.shared.services.stock.api;

import com.lazy.tcc.common.enums.ApplicationRole;
import com.lazy.tcc.core.annotation.Compensable;
import com.lazy.tcc.core.annotation.Idempotent;
import com.lazy.tcc.example.dubbo.shared.services.stock.api.dto.SimpleResponseDto;
import com.lazy.tcc.example.dubbo.shared.services.stock.api.dto.StockEditorDto;
import com.lazy.tcc.dubbo.propagator.DubboIdempotentContextPropagator;
import com.lazy.tcc.dubbo.propagator.DubboTransactionContextPropagator;

/**
 * <p>
 * Definition A Stock Service Interface
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/10.
 */
public interface IStockService {

    /**
     * TCC - Try Deduct Stock Api
     *
     * @param dto param {@link StockEditorDto}
     * @return Operation Result {@link SimpleResponseDto } {@link String}
     */
    @Compensable(propagator = DubboTransactionContextPropagator.class)
    SimpleResponseDto<String> deductStock(StockEditorDto dto);


    /**
     * TCC - Confirm Deduct Stock Api
     *
     * @param dto param {@link StockEditorDto}
     * @return Operation Result {@link SimpleResponseDto } {@link String}
     */
    @Idempotent(applicationRole = ApplicationRole.CONSUMER, propagator = DubboIdempotentContextPropagator.class)
    SimpleResponseDto<String> confirmDeductStock(StockEditorDto dto);


    /**
     * TCC - Cancel Deduct Stock Api
     *
     * @param dto param {@link StockEditorDto}
     * @return Operation Result {@link SimpleResponseDto } {@link String}
     */
    @Idempotent(applicationRole = ApplicationRole.CONSUMER, propagator = DubboIdempotentContextPropagator.class)
    SimpleResponseDto<String> cancelDeductStock(StockEditorDto dto);

}
