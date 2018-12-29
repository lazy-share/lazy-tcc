package com.lazy.tcc.example.dubbo.shared.services.stock.api.entity;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 * TStockEntity
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/10.
 */
@Entity
@Table(name = "t_stock")
@EqualsAndHashCode
public class TStockEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8941982L;


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String productSku;
    private Integer stockNum;
    private Integer frozenNum;
    @Version
    private long version;

    public Long getId() {
        return id;
    }

    public TStockEntity setId(Long id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "frozen_num")
    public Integer getFrozenNum() {
        return frozenNum;
    }

    public TStockEntity setFrozenNum(Integer frozenNum) {
        this.frozenNum = frozenNum;
        return this;
    }

    @Basic
    @Column(name = "product_sku")
    public String getProductSku() {
        return productSku;
    }

    public TStockEntity setProductSku(String productSku) {
        this.productSku = productSku;
        return this;
    }

    @Basic
    @Column(name = "stock_num")
    public Integer getStockNum() {
        return stockNum;
    }

    public TStockEntity setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
        return this;
    }

    @Basic
    @Column(name = "version")
    public long getVersion() {
        return version;
    }

    public TStockEntity setVersion(long version) {
        this.version = version;
        return this;
    }
}
