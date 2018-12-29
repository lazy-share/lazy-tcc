package com.lazy.tcc.example.dubbo.shared.services.customer.repository;

import com.lazy.tcc.example.dubbo.shared.services.customer.api.entity.TCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

/**
 * <p>
 * ICustomerRepository
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/11.
 */
public interface ICustomerRepository extends JpaRepository<TCustomerEntity, Long> {

    /**
     * TCC - Try Deduct Capital Api
     *
     * @param customerNo customerNo {@link String}
     * @param capital    capital {@link BigDecimal}
     */
    @Modifying
    @Query(
            "update from TCustomerEntity set totalCapital = totalCapital - ?2, frozenCapital = frozenCapital + ?2 where customerNo = ?1"
    )
    void deductCapital(String customerNo, BigDecimal capital);


    /**
     * TCC - Confirm Deduct Capital Api
     *
     * @param customerNo customerNo {@link String}
     * @param capital    capital {@link BigDecimal}
     */
    @Modifying
    @Query(
            "update from TCustomerEntity set frozenCapital = frozenCapital - ?2 where customerNo = ?1"
    )
    void confirmDeductCapital(String customerNo, BigDecimal capital);


    /**
     * TCC - Cancel Deduct Capital Api
     *
     * @param customerNo customerNo {@link String}
     * @param capital    capital {@link BigDecimal}
     */
    @Modifying
    @Query(
            "update from TCustomerEntity set frozenCapital = frozenCapital - ?2, totalCapital = totalCapital + ?2 where customerNo = ?1"
    )
    void cancelDeductCapital(String customerNo, BigDecimal capital);

}
