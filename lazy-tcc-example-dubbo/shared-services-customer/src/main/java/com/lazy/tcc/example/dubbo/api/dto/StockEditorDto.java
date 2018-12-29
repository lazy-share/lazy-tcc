package com.lazy.tcc.example.dubbo.api.dto;

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


    private String productNo;
    private Integer stockNum;

    public String getProductNo() {
        return productNo;
    }

    public StockEditorDto setProductNo(String productNo) {
        this.productNo = productNo;
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
