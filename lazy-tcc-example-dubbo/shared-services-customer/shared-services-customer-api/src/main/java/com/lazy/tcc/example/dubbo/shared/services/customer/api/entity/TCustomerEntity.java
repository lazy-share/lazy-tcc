package com.lazy.tcc.example.dubbo.shared.services.customer.api.entity;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/11.
 */
@Table(name = "t_customer")
@Entity
@EqualsAndHashCode
public class TCustomerEntity implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 897857441982L;


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String customerNo;
    private BigDecimal totalCapital;
    private BigDecimal frozenCapital;
    @Version
    private long version;

    public Long getId() {
        return id;
    }

    public TCustomerEntity setId(Long id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "customer_no")
    public String getCustomerNo() {
        return customerNo;
    }

    public TCustomerEntity setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
        return this;
    }

    @Basic
    @Column(name = "total_capital")
    public BigDecimal getTotalCapital() {
        return totalCapital;
    }

    public TCustomerEntity setTotalCapital(BigDecimal totalCapital) {
        this.totalCapital = totalCapital;
        return this;
    }

    @Basic
    @Column(name = "frozen_capital")
    public BigDecimal getFrozenCapital() {
        return frozenCapital;
    }

    public TCustomerEntity setFrozenCapital(BigDecimal frozenCapital) {
        this.frozenCapital = frozenCapital;
        return this;
    }

    @Basic
    @Column(name = "version")
    public long getVersion() {
        return version;
    }

    public TCustomerEntity setVersion(long version) {
        this.version = version;
        return this;
    }
}
