package com.lazy.tcc.example.dubbo.aggregate.services.retail.repository;


import com.lazy.tcc.example.dubbo.aggregate.services.retail.entity.TOrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * IOrderItemRepository
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/10.
 */
public interface IOrderItemRepository extends JpaRepository<TOrderItemEntity, Long> {


}
