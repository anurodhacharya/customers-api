package com.aurickcode.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aurickcode.exception.ResourceNotFound;

@Service // we say this so that this class is instantiated for us. // This class's object will be kept in that Application Context
public class CustomerService {
    private final CustomerDAO customerDAO;
    // private final CustomerDAO customerDataAccessService;

    public CustomerService(@Qualifier("jpa") CustomerDAO customerDataAccessService) {
        this.customerDAO = customerDataAccessService;
    }

    /*
     * Above we are making customerDataAccessService ready to point to any class's object that implements CustomerDAO 
     * interface.
     *  Now, using beans, it will try to inject a particular object at the beginning itself, however since there are more
     * than one class's object that it might be referencing to, so we put @Qualifier("jpa") to specify which class's object
     * we want to inject.
     */

    public List<Customer> getAllCustomers() {
        return customerDAO.selectAllCustomers();
    }

    public Customer getCustomer(Integer id) {
        return customerDAO.selectCustomerById(id)
        .orElseThrow(() -> new ResourceNotFound("customer with id [%s] not found".formatted(id)));
    }
}
