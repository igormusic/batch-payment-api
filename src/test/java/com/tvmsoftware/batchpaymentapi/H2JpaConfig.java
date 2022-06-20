package com.tvmsoftware.batchpaymentapi;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.tvmsoftware.batchpaymentapi.model")
@PropertySource("classpath:application-test.properties")
@EnableTransactionManagement
public class H2JpaConfig {
    // ...
}
