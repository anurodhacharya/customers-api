package com.aurickcode.customer;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CustomerJPADataAccessServiceTest {
    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testDeleteCustomerById() {
        Long id = 1L;

        underTest.deleteCustomerById(id);

        Mockito.verify(customerRepository).deleteById(id);
    }

    @Test
    void testExistsPersonWithEmail() {
        String email = "foo@gmail.com";

        underTest.existsPersonWithEmail(email);
        verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void testExistsPersonWithId() {
        Long id = 2L;

        underTest.existsPersonWithId(id);
        verify(customerRepository).existsCustomerById(id);
    }

    @Test
    void testInsertCustomer() {
        Customer customer = new Customer(1L, "Ali", "ali@gmail.com", "password", 2, "MALE");

        underTest.insertCustomer(customer);

        Mockito.verify(customerRepository).save(customer);
    }


    // Here, we are making sure that findAll method of customerRepository will be called.
    @Test
    void testSelectAllCustomers() {
        underTest.selectAllCustomers();

        Mockito.verify(customerRepository).findAll();
    }


    // Here, we are verifying that whatever is sent, that is given to the findById.
    @Test
    void testSelectCustomerById() {
        Long id = 1L;

        underTest.selectCustomerById(id);

        Mockito.verify(customerRepository).findById(id);
    }

    @Test
    void testUpdateCustomerInfo() {
        Customer customer = new Customer(1L, "Ali", "alli@gmail.com", "password", 2, "MALE");

        underTest.updateCustomerInfo(customer);
        Mockito.verify(customerRepository).save(customer);
    }
}
