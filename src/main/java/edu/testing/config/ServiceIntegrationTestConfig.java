package edu.testing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
class ServiceIntegrationTestConfig {

    @Bean
    JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(embeddedDatabase());
    }

    @Lazy
    @Bean
    EmbeddedDatabase embeddedDatabase() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("create-test-db.sql")
                .addScript("insert-test-data.sql")
                .build();
    }
}
