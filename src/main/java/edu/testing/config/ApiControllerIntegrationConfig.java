package edu.testing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Config for account-API.
 */
public class ApiControllerIntegrationConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
