package com.lazy.tcc.dubbo.springboot.autoconfig;

import com.lazy.tcc.core.InvokerCaller;
import com.lazy.tcc.dubbo.DubboInvokerCaller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/23.
 */
@Configuration
public class LazyTccDubboAutoConfiguration {

    @Bean
    public InvokerCaller invokerCaller() {
        return new DubboInvokerCaller();
    }
}
