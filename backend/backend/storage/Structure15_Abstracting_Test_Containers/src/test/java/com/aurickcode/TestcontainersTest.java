package com.aurickcode;

import static org.junit.Assert.assertTrue;

import org.apache.catalina.core.ApplicationContext;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public class TestcontainersTest {

    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
                                                            .withDatabaseName("aurickcode-dao-unit-test")
                                                            .withUsername("aurickcode")
                                                            .withPassword("aurickpassword");

    @DynamicPropertySource
    private static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> postgreSQLContainer.getJdbcUrl());
        registry.add("spring.datasource.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> postgreSQLContainer.getPassword());
    }

    @Test
    void canStartPostgresDB() {
        assertTrue(postgreSQLContainer.isRunning());
        assertTrue(postgreSQLContainer.isCreated());
    }

    @Test
    void canApplyDbMigrationsWithFlyway() {
        Flyway flyway = Flyway.configure().dataSource(
                    postgreSQLContainer.getJdbcUrl(), 
                    postgreSQLContainer.getUsername(), 
                    postgreSQLContainer.getPassword()).load(); 
        flyway.migrate();
        System.out.println();
    }
    
}
