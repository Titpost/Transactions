package edu.testing.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Configuration for Account Service testing.
 */
@Configuration
@ComponentScan(basePackages = {
        "edu.billing.service.implementations",
        "edu.billing.dao"
})
public class AccountServiceIntegrationTestConfig{

    @Bean
    JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(embeddedDatabase());
    }

    @Lazy
    @Bean
    EmbeddedDatabase embeddedDatabase() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("testingDataBaseScripts/create-test-db.sql")
                .addScript("testingDataBaseScripts/insert-test-data.sql")
                .build();
    }
}
