package com.lazy.tcc.core.cache.guava;

import com.google.common.cache.CacheBuilder;
import com.lazy.tcc.core.cache.support.AbstractCache;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * GoogleGuavaCache Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
public class GoogleGuavaCache<K, V> extends AbstractCache<K, V> {

    private final com.google.common.cache.Cache<K, V> cache;

    /**
     * Simple Construct
     */
    public GoogleGuavaCache() {
        //120 sec
        int defaultExpire = 120;
        this.cache = CacheBuilder.newBuilder().expireAfterAccess(defaultExpire, TimeUnit.SECONDS).maximumSize(1000).build();
    }

    /**
     * put
     *
     * @param k key
     * @param v val
     */
    @Override
    public void put(K k, V v) {
        cache.put(k, v);
    }

    /**
     * get
     *
     * @param k key
     * @return val
     */
    @Override
    public V get(K k) {
        return cache.getIfPresent(k);
    }

    /**
     * remove
     *
     * @param k key
     */
    @Override
    public void remove(K k) {
        cache.invalidate(k);
    }

    /**
     * clean all
     */
    @Override
    public void cleanAll() {
        cache.cleanUp();
    }
}
