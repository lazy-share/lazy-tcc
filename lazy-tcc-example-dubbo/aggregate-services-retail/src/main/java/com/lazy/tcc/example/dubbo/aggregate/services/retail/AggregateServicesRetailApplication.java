package com.lazy.tcc.example.dubbo.aggregate.services.retail;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDubbo(scanBasePackages = {
        "com.lazy.tcc.example.dubbo.aggregate.services.retail.service"
})
@EnableJpaRepositories(
        value = {
                "com.lazy.tcc.example.dubbo.aggregate.services.retail.repository"
        }
)
@EntityScan(value = {
        "com.lazy.tcc.example.dubbo.aggregate.services.retail.entity"
})
@ComponentScan({"com.lazy.tcc.*"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@SpringBootApplication
public class AggregateServicesRetailApplication {

    public static void main(String[] args) {
        SpringApplication.run(AggregateServicesRetailApplication.class, args);
    }
}
