package com.lazy.tcc.example.dubbo.aggregate.services.retail.entity;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * TOrderItemEntity
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/10.
 */
@Entity
@Table(name = "t_order_item")
@EqualsAndHashCode
public class TOrderItemEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -97888941982L;


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String productSku;
    private Integer productNum;
    private String orderNo;
    private BigDecimal totalAmount;

    public Long getId() {
        return id;
    }

    public TOrderItemEntity setId(Long id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "product_num")
    public Integer getProductNum() {
        return productNum;
    }

    public TOrderItemEntity setProductNum(Integer productNum) {
        this.productNum = productNum;
        return this;
    }

    @Basic
    @Column(name = "order_no")
    public String getOrderNo() {
        return orderNo;
    }

    public TOrderItemEntity setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    @Basic
    @Column(name = "product_sku")
    public String getProductSku() {
        return productSku;
    }

    public TOrderItemEntity setProductSku(String productSku) {
        this.productSku = productSku;
        return this;
    }

    @Basic
    @Column(name = "total_amount")
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public TOrderItemEntity setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }
}
