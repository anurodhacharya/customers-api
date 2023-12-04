package com.aurickcode.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerDAO customerDAO;

    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDAO);
    }

    @Test
    void testAddCustomer() {

    }

    @Test
    void testDeleteCustomer() {

    }

    @Test
    void testGetAllCustomers() {
        underTest.getAllCustomers();
        Mockito.verify(customerDAO).selectAllCustomers();
    }

    // It seems to check whether the getCustomer 
    @Test
    void testCanGetCustomer() {
        Long id = 10L;

        Customer customer = new Customer(id, "Alex", "alex@gmail.com", 19);
        Mockito.when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));

        Customer actual = underTest.getCustomer(10L);

        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void testUpdateCustomerInfo() {

    }
}
