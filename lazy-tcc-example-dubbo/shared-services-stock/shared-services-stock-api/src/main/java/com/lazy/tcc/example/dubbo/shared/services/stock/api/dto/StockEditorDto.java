package com.lazy.tcc.example.dubbo.shared.services.stock.api.dto;

/**
 * <p>
 * Definition A Edit Stock Dto
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/10.
 */
public class StockEditorDto extends AbstractBasicDto {

    /**
     *
     */
    private static final Long serialVersionUID = -89569569862L;


    private String productSku;
    private Integer stockNum;

    public String getProductSku() {
        return productSku;
    }

    public StockEditorDto setProductSku(String productSku) {
        this.productSku = productSku;
        return this;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public StockEditorDto setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
        return this;
    }
}
