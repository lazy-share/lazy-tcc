package com.lazy.tcc.core.cache;

/**
 * <p>
 * ICache Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
public interface Cache<K, V> {

    /**
     * put
     *
     * @param k key
     * @param v val
     */
    void put(K k, V v);

    /**
     * get
     *
     * @param k key
     * @return val
     */
    V get(K k);

    /**
     * remove
     *
     * @param k key
     */
    void remove(K k);

    /**
     * clean all
     */
    void cleanAll();

}
