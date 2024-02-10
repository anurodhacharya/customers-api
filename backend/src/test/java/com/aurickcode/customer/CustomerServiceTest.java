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
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerDAO customerDAO;

    @Mock
    private PasswordEncoder passwordEncoder;
    private CustomerService underTest;
    private final CustomerDTOMapper customerDTOMapper = new CustomerDTOMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDAO, customerDTOMapper, passwordEncoder);
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

        Customer customer = new Customer(id, "Alex", "alex@gmail.com", "password", 19, "MALE");
        Mockito.when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerDTO expected = customerDTOMapper.apply(customer);

        CustomerDTO actual = underTest.getCustomer(10L);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testUpdateCustomerInfo() {

    }
}
