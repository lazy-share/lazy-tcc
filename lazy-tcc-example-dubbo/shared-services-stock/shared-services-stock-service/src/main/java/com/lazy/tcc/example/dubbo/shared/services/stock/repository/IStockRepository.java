package com.lazy.tcc.example.dubbo.shared.services.stock.repository;


import com.lazy.tcc.example.dubbo.shared.services.stock.api.entity.TStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * <p>
 * IStockRepository
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/10.
 */
public interface IStockRepository extends JpaRepository<TStockEntity, Long> {

    /**
     * TCC - Try Deduct Stock Api
     *
     * @param productSku ProductNo {@link String}
     * @param stockNum   stockNum {@link Integer}
     */
    @Modifying
    @Query(
            "update from TStockEntity set stockNum = stockNum - ?2, frozenNum = frozenNum + ?2 where productSku = ?1"
    )
    void deductStock(String productSku, Integer stockNum);


    /**
     * TCC - Confirm Deduct Stock Api
     *
     * @param productSku ProductNo {@link String}
     * @param stockNum   stockNum {@link Integer}
     */
    @Modifying
    @Query(
            "update from TStockEntity set frozenNum = frozenNum - ?2 where productSku = ?1"
    )
    void confirmDeductStock(String productSku, Integer stockNum);


    /**
     * TCC - Cancel Deduct Stock Api
     *
     * @param productSku ProductNo {@link String}
     * @param stockNum   stockNum {@link Integer}
     */
    @Modifying
    @Query(
            "update from TStockEntity set frozenNum = frozenNum - ?2, stockNum = stockNum + ?2 where productSku = ?1"
    )
    void cancelDeductStock(String productSku, Integer stockNum);

}
