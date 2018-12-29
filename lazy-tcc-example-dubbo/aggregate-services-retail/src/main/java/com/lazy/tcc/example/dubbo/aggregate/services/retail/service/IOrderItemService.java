package com.lazy.tcc.example.dubbo.aggregate.services.retail.service;

import com.lazy.tcc.example.dubbo.aggregate.services.retail.entity.TOrderItemEntity;

/**
 * <p>
 * IOrderItemService
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/11.
 */
public interface IOrderItemService {

    void save(TOrderItemEntity entity);

}
