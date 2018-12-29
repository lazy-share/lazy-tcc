package com.lazy.tcc.example.dubbo.api;

import com.lazy.tcc.example.dubbo.api.dto.SimpleResponseDto;
import com.lazy.tcc.example.dubbo.api.dto.StockEditorDto;

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
    SimpleResponseDto<String> tryDeductStock(StockEditorDto dto);


    /**
     * TCC - Confirm Deduct Stock Api
     *
     * @param dto param {@link StockEditorDto}
     * @return Operation Result {@link SimpleResponseDto } {@link String}
     */
    SimpleResponseDto<String> confirmDeductStock(StockEditorDto dto);


    /**
     * TCC - Cancel Deduct Stock Api
     *
     * @param dto param {@link StockEditorDto}
     * @return Operation Result {@link SimpleResponseDto } {@link String}
     */
    SimpleResponseDto<String> cancelDeductStock(StockEditorDto dto);


    /**
     * Definition Response Successfully
     *
     * @return {@link SimpleResponseDto }
     */
    default SimpleResponseDto<String> responseSuccessfully() {
        SimpleResponseDto<String> responseDto = new SimpleResponseDto<>();
        responseDto.setData(null);
        responseDto.setMsg("success");
        responseDto.setCode("200");
        return responseDto;
    }
}
