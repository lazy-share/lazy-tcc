package com.lazy.tcc.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;

/**
 * <p>
 * BeanFactory
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/16.
 */
public class BeanFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private static BeanFactory single;

    @PostConstruct
    public void init() {
        if (single == null) {
            synchronized (BeanFactory.class) {
                if (single == null) {
                    single = this;
                }
            }
        }
    }

    public static BeanFactory getSingle() {
        return single;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
