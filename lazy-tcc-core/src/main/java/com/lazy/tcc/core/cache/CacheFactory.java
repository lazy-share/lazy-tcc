package com.lazy.tcc.core.cache;


import com.lazy.tcc.core.spi.SpiConfiguration;

/**
 * <p>
 * CacheFactory Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
public final class CacheFactory {

    private CacheFactory(){}

    private static volatile Cache cache;

    public static <K, V> Cache<K, V> create() {
        if (cache == null) {
            synchronized (CacheFactory.class) {
                if (cache == null) {
                    try {
                        cache = SpiConfiguration.getInstance().getCacheClassImpl().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return cache;
    }

}
