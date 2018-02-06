package edu.testing.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Account Service testing.
 */
@Configuration
@ComponentScan(basePackages = {
        "edu.billing.service.implementations",
        "edu.billing.dao"
})
public class AccountServiceIntegrationTestConfig extends ServiceIntegrationTestConfig {}
