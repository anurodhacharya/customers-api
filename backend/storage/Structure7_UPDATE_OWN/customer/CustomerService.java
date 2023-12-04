package com.aurickcode.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aurickcode.exception.DuplicateResourceException;
import com.aurickcode.exception.ResourceNotFound;

@Service // we say this so that this class is instantiated for us. // This class's object will be kept in that Application Context
public class CustomerService {
    private final CustomerDAO customerDAO;
    // private final CustomerDAO customerDataAccessService;

    public CustomerService(@Qualifier("jpa") CustomerDAO customerDataAccessService) {
        this.customerDAO = customerDataAccessService;
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.selectAllCustomers();
    }

    public Customer getCustomer(Integer id) {
        return customerDAO.selectCustomerById(id)
        .orElseThrow(() -> new ResourceNotFound("customer with id [%s] not found".formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        if(customerDAO.existsPersonWithEmail(customerRegistrationRequest.email())) {
            throw new DuplicateResourceException("Email already taken");
        }
        Customer customer = new Customer(
                    customerRegistrationRequest.name(),
                    customerRegistrationRequest.email(),
                    customerRegistrationRequest.age()
                );
        customerDAO.insertCustomer(customer);
    }

    public void deleteCustomer(Integer customerId) {
        if(!customerDAO.existsPersonWithId(customerId)) {
            throw new ResourceNotFound("person does not exist");
        }
        customerDAO.deleteCustomerById(customerId);
    }

    public void updateCustomerInfo(Integer id, CustomerRegistrationRequest request) {
        Customer customer = customerDAO.selectCustomerById(id)
        .orElseThrow(() -> new ResourceNotFound("customer with id [%s] not found".formatted(id)));

        customer.setName(request.name());
        customer.setAge(request.age());
        customer.setEmail(request.email());

        customerDAO.updateCustomerInfo(customer);
    }
}
