package com.lazy.tcc.example.dubbo.aggregate.services.retail.entity;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * TOrderEntity
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/10.
 */
@Entity
@Table(name = "t_order")
@EqualsAndHashCode
public class TOrderEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -97888941982L;


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String customerNo;
    private BigDecimal totalAmount;
    private String orderNo;
    @Transient
    private List<TOrderItemEntity> itemEntityList;

    @Transient
    public List<TOrderItemEntity> getItemEntityList() {
        return itemEntityList;
    }

    public TOrderEntity setItemEntityList(List<TOrderItemEntity> itemEntityList) {
        this.itemEntityList = itemEntityList;
        return this;
    }

    public Long getId() {
        return id;
    }

    public TOrderEntity setId(Long id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "customer_no")
    public String getCustomerNo() {
        return customerNo;
    }

    public TOrderEntity setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
        return this;
    }

    @Basic
    @Column(name = "total_amount")
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public TOrderEntity setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    @Basic
    @Column(name = "order_no")
    public String getOrderNo() {
        return orderNo;
    }

    public TOrderEntity setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }
}
