package com.lazy.tcc.core.repository.support;

import com.lazy.tcc.core.cache.Cache;
import com.lazy.tcc.core.cache.CacheFactory;
import com.lazy.tcc.core.entity.support.BasicEntity;

import java.io.Serializable;

/**
 * <p>
 * AbstractDataSourceRepository Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public abstract class AbstractCacheRepository<E extends BasicEntity, ID extends Serializable> extends AbstractDataSourceRepository<E, ID> {

    private Cache<ID, E> cache = CacheFactory.create();

    @Override
    public int insert(E entity) {
        int count = this.doInsert(entity);
        if (count > 0) {
            cache.put((ID) entity.cacheKey(), entity);
        }
        return count;
    }

    @Override
    public int update(E entity) {
        int count = this.doUpdate(entity);
        if (count > 0) {
            cache.put((ID) entity.cacheKey(), entity);
        }
        return count;
    }

    @Override
    public int delete(ID id) {
        int count = this.doDelete(id);
        if (count > 0) {
            cache.remove(id);
        }
        return count;
    }

    @Override
    public E findById(ID id) {
        E tx = cache.get(id);
        if (tx == null) {
            tx = this.doFindById(id);
            if (tx != null) {
                cache.put(id, tx);
            }
        }
        return tx;
    }


    public abstract int doInsert(E entity);

    public abstract int doUpdate(E entity);

    public abstract int doDelete(ID id);

    public abstract E doFindById(ID id);

}
