package com.aurickcode.customer;

import org.springframework.context.ApplicationContext;
import com.aurickcode.AbstractTestContainers;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest extends AbstractTestContainers {

    @Autowired
    private CustomerRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    void testExistsCustomerByEmail() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
            FAKER.name().firstName(),
            email,
            20,
            "men"
        );

        underTest.save(customer);
        boolean actual = underTest.existsCustomerByEmail(email);

        assertThat(actual).isTrue();
    }

    @Test
    void testExistsCustomerByEmailFailsWhenEmailNotPresent() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        boolean actual = underTest.existsCustomerByEmail(email);

        assertThat(actual).isFalse();
    }

    @Test
    void testExistsCustomerById() {

        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
            FAKER.name().firstName(),
            email,
            20,
            "men"
        );

        underTest.save(customer);

        Long id = underTest.findAll()
            .stream()
            .filter(c -> c.getEmail().equals(email))
            .map(Customer::getId)
            .findFirst()
            .orElseThrow();

        boolean actual = underTest.existsCustomerById(id);

        assertThat(actual).isTrue();
    }

    @Test
    void testExistsCustomerByIdWhenIdNotPresent() {

        Long id = -1L;
        boolean actual = underTest.existsCustomerById(id);

        assertThat(actual).isFalse();
    }
}
