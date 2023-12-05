package com.aurickcode;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.github.javafaker.Faker;

@Testcontainers
public abstract class AbstractTestContainers {

    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway.configure().dataSource(
                    postgreSQLContainer.getJdbcUrl(), 
                    postgreSQLContainer.getUsername(), 
                    postgreSQLContainer.getPassword()).load(); 
        flyway.migrate();
        System.out.println();
    }

    @Container
    protected static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
                                                            .withDatabaseName("aurickcode-dao-unit-test")
                                                            .withUsername("aurickcode")
                                                            .withPassword("aurickpassword");

    @DynamicPropertySource
    private static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> postgreSQLContainer.getJdbcUrl());
        registry.add("spring.datasource.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> postgreSQLContainer.getPassword());
    }

    private static DataSource getDataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create()
                                        .driverClassName(postgreSQLContainer.getDriverClassName())
                                        .url(postgreSQLContainer.getJdbcUrl())
                                        .username(postgreSQLContainer.getUsername())
                                        .password(postgreSQLContainer.getPassword());

        return builder.build();
    }

    protected static JdbcTemplate getjJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    protected static final Faker FAKER = new Faker();
}
