package com.aurickcode.customer;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.aurickcode.AbstractTestContainers;

@Repository
public class CustomerJDBCDataAccessServiceTest extends AbstractTestContainers {
    private CustomerJDBCDataAccessService underTest;
    // private JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    // public CustomerJDBCDataAccessServiceTest(JdbcTemplate jdbcTemplate) {
    //     this.jdbcTemplate = jdbcTemplate;
    // }

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(getjJdbcTemplate(), customerRowMapper);
    }
    
    // @BeforeEach
    // void setUp() {
    //     underTest = new CustomerJDBCDataAccessService(jdbcTemplate, customerRowMapper);
    // }

    @Test
    void testDeleteCustomerById() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
            FAKER.name().firstName(),
            email,
            20
        );
        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers()
            .stream()
            .filter(c -> c.getEmail().equals(email))
            .map(Customer::getId)
            .findFirst()
            .orElseThrow();

        underTest.deleteCustomerById(id);

        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isNotPresent();
    }

    @Test
    void testExistsPersonWithEmail() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
            FAKER.name().firstName(),
            email,
            20
        );
        underTest.insertCustomer(customer);

        boolean actual = underTest.existsPersonWithEmail(email);
        assertThat(actual).isTrue();
    }

    @Test
    void existsPersonWithEmailReturnsFalseWhenDoesNotExists() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        boolean actual = underTest.existsPersonWithEmail(email);
        assertThat(actual).isFalse();
    }

    @Test
    void testExistsPersonWithId() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
            FAKER.name().firstName(),
            email,
            20
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers()
            .stream()
            .filter(c -> c.getEmail().equals(email))
            .map(Customer::getId)
            .findFirst()
            .orElseThrow();

        boolean actual = underTest.existsPersonWithId(id);

        assertThat(actual).isTrue();
    }

    @Test
    void existsPersonWithIdWillReturnFalseWhenIdNotPresent() {
        Long id = -1L;

        boolean actual = underTest.existsPersonWithId(id);

        assertThat(actual).isFalse();
    }

    @Test
    void testInsertCustomer() {

    }

    @Test
    void testSelectAllCustomers() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
            FAKER.name().firstName(),
            email,
            20
        );
        underTest.insertCustomer(customer);

        List<Customer> actual = underTest.selectAllCustomers();

        assertThat(actual).isNotEmpty();
    }

    @Test
    void testSelectCustomerById() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
            FAKER.name().firstName(),
            email,
            20
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers()
            .stream()
            .filter(c -> c.getEmail().equals(email))
            .map(Customer::getId)
            .findFirst()
            .orElseThrow();

        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void willReturnEmptyWhenSelectCustomerById() {
        Long id = 1L;

        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isEmpty();
    }

    @Test
    void testUpdateCustomerName() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
            FAKER.name().firstName(),
            email,
            20
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers()
            .stream()
            .filter(c -> c.getEmail().equals(email))
            .map(Customer::getId)
            .findFirst()
            .orElseThrow();

        String newName = "foo";

        Customer update = new Customer();
        update.setId(id);
        update.setName(newName);

        underTest.updateCustomerInfo(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(newName);
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });

    }

    @Test
    void testUpdateCustomerEmail() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
            FAKER.name().firstName(),
            email,
            20
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers()
            .stream()
            .filter(c -> c.getEmail().equals(email))
            .map(Customer::getId)
            .findFirst()
            .orElseThrow();

        String newEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        Customer update = new Customer();
        update.setId(id);
        update.setEmail(newEmail);

        underTest.updateCustomerInfo(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(newEmail);
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });

    }

    @Test
    void testUpdateCustomerAge() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
            FAKER.name().firstName(),
            email,
            20
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers()
            .stream()
            .filter(c -> c.getEmail().equals(email))
            .map(Customer::getId)
            .findFirst()
            .orElseThrow();

        int newAge = 30;

        Customer update = new Customer();
        update.setId(id);
        update.setAge(newAge);

        underTest.updateCustomerInfo(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(newAge);
        });
    }

    @Test
    void willUpdateAllPropertiesCustomer() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
            FAKER.name().firstName(),
            email,
            20
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers()
            .stream()
            .filter(c -> c.getEmail().equals(email))
            .map(Customer::getId)
            .findFirst()
            .orElseThrow();

        Customer update = new Customer();
        update.setId(id);
        update.setName("foo");
        update.setEmail(UUID.randomUUID().toString());
        update.setAge(22);

        underTest.updateCustomerInfo(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValue(update);
    }

    @Test
    void willNotUpdateWhenNothingToUpdate() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
            FAKER.name().firstName(),
            email,
            20
        );

        underTest.insertCustomer(customer);

        Long id = underTest.selectAllCustomers()
            .stream()
            .filter(c -> c.getEmail().equals(email))
            .map(Customer::getId)
            .findFirst()
            .orElseThrow();

        Customer update = new Customer();
        update.setId(id);

        underTest.updateCustomerInfo(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }
}
