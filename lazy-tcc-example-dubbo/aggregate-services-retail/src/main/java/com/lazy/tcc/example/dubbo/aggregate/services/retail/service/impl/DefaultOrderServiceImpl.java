package com.lazy.tcc.example.dubbo.aggregate.services.retail.service.impl;

import com.lazy.tcc.example.dubbo.aggregate.services.retail.entity.TOrderEntity;
import com.lazy.tcc.example.dubbo.aggregate.services.retail.repository.IOrderRepository;
import com.lazy.tcc.example.dubbo.aggregate.services.retail.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Definition A Default Order Service Impl
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/10.
 */
@org.springframework.stereotype.Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class DefaultOrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderRepository repository;


    @Override
    public void save(TOrderEntity entity) {
        repository.save(entity);
    }
}
