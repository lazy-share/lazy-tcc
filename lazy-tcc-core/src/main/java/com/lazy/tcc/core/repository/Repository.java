package com.lazy.tcc.core.repository;

import com.lazy.tcc.core.entity.support.BasicEntity;

import java.io.Serializable;

/**
 * <p>
 * Repository Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/17.
 */
public interface Repository<E extends BasicEntity, ID extends Serializable> {

    int insert(E entity);

    int update(E entity);

    int delete(ID id);

    E findById(ID id);

    int createTable();

    boolean exists(ID id);
}
