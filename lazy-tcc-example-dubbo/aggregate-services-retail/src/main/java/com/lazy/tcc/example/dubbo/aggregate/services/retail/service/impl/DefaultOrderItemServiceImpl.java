package com.lazy.tcc.example.dubbo.aggregate.services.retail.service.impl;

import com.lazy.tcc.example.dubbo.aggregate.services.retail.entity.TOrderItemEntity;
import com.lazy.tcc.example.dubbo.aggregate.services.retail.repository.IOrderItemRepository;
import com.lazy.tcc.example.dubbo.aggregate.services.retail.service.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Definition A Default Order Item Service Impl
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/10.
 */
@org.springframework.stereotype.Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class DefaultOrderItemServiceImpl implements IOrderItemService {

    @Autowired
    private IOrderItemRepository repository;


    @Override
    public void save(TOrderItemEntity entity) {
        repository.save(entity);
    }
}
