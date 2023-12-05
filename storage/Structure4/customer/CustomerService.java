package com.aurickcode.customer;

import java.util.List;
import org.springframework.stereotype.Service;

import com.aurickcode.exception.ResourceNotFound;

@Service // we say this so that this class is instantiated for us. // This class's object will be kept in that Application Context
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
        .orElseThrow(() -> new ResourceNotFound("customer with id [%s] not found".formatted(id)));
    }
}
