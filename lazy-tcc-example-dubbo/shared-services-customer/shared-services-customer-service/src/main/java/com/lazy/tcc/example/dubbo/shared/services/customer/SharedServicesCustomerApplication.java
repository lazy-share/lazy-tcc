package com.lazy.tcc.example.dubbo.shared.services.customer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDubbo(scanBasePackages = {
        "com.lazy.tcc.example.dubbo.shared.services.customer.service"
})
@EnableJpaRepositories(
        value = {
                "com.lazy.tcc.example.dubbo.shared.services.customer.repository"
        }
)
@EntityScan(value = {
        "com.lazy.tcc.example.dubbo.shared.services.customer.api.entity"
})
@EnableAspectJAutoProxy
@SpringBootApplication
public class SharedServicesCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharedServicesCustomerApplication.class, args);
    }
}
