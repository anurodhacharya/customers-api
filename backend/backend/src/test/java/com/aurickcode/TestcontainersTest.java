package com.aurickcode;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public class TestcontainersTest extends AbstractTestContainers {
    @Test
    void canStartPostgresDB() {
        assertTrue(postgreSQLContainer.isRunning());
        assertTrue(postgreSQLContainer.isCreated());
    }
    
}
