package com.lazy.tcc.core.spi;

import com.lazy.tcc.common.utils.Assert;
import com.lazy.tcc.common.utils.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * <p>通过Properties获取配置文件内容</p>
 *
 * @author laizhiyuan
 * @since 2018/3/26.
 */
public class PropertiesReader {

    private Properties mainProp;
    private static volatile PropertiesReader instance = null;

    /**
     * 单例线程安全
     *
     * @return
     */
    public static PropertiesReader getInstance() {
        synchronized (PropertiesReader.class) {
            if (instance == null) {
                instance = new PropertiesReader();
            }
        }
        return instance;
    }

    private PropertiesReader() {
        mainProp = new Properties();
        InputStream applicationInputStream = null;
        BufferedReader applicationBr = null;
        try {
            Resource applicationResource = new ClassPathResource("application-lazy-tcc.properties");
            applicationInputStream = applicationResource.getInputStream();
            applicationBr = new BufferedReader(new InputStreamReader(applicationInputStream, "UTF-8"));
            mainProp.load(applicationBr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (applicationInputStream != null) {
                try {
                    applicationInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (applicationBr != null) {
                    try {
                        applicationBr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * @param key
     * @return
     */
    public EnvVal getProp(String key) {
        String value = mainProp.getProperty(key);
        Assert.notNull(key, String.format("key %s val is null", key));
        return new EnvVal(value);
    }

    /**
     * application-${env}.properties
     *
     * @param key
     * @return
     */
    public EnvVal getProp(String key, String defaultValue) {
        String value = mainProp.getProperty(key);
        if (StringUtils.isBlank(value)) {
            return new EnvVal(defaultValue);
        }
        return new EnvVal(value);
    }

    /**
     * 环境值对象
     */
    public static class EnvVal {
        private String val;

        public EnvVal(String val) {
            this.val = val;
        }

        public Long getLongVal() {
            return StringUtils.isBlank(val) ? null : Long.valueOf(val);
        }

        public Integer getIntVal() {
            return StringUtils.isBlank(val) ? null : Integer.valueOf(val);
        }

        public Boolean getBooleanVal() {
            return StringUtils.isBlank(val) ? false : Boolean.valueOf(val);
        }

        public String getVal() {
            return val;
        }
    }
}
