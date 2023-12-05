package com.aurickcode.customer;

import java.util.List;
import org.springframework.stereotype.Service;

@Service // we say this so that this class is instantiated for us.
public class CustomerService {
    private final CustomerDAO customerDAO;
    // private final CustomerDAO customerDataAccessService;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.selectAllCustomers();
    }

    public Customer getCustomer(Integer id) {
        return customerDAO.selectCustomerById(id)
        .orElseThrow(() -> new IllegalArgumentException("customer with id not found".formatted(id)));
    }
}
