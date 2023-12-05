package com.aurickcode.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aurickcode.exception.DuplicateResourceException;
import com.aurickcode.exception.RequestValidationException;
import com.aurickcode.exception.ResourceNotFound;

@Service // we say this so that this class is instantiated for us. // This class's object will be kept in that Application Context
public class CustomerService {
    private final CustomerDAO customerDAO;
    // private final CustomerDAO customerDataAccessService;

    public CustomerService(@Qualifier("jdbc") CustomerDAO customerDataAccessService) {
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

    public void updateCustomerInfo(Integer id, CustomerUpdateRequest updateRequest) {
        Customer customer = getCustomer(id);

        boolean changes = false;

        if(updateRequest.name() != null && !updateRequest.name().equals(customer.getName())) {
            customer.setName(updateRequest.name());
            changes = true; 
        }

        if(updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())) {
            customer.setAge(updateRequest.age());
            changes = true;
        }
        
        if(updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())) {
            if(customerDAO.existsPersonWithEmail(updateRequest.email())) {
                throw new DuplicateResourceException("Email already taken");
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if(!changes) {
            throw new RequestValidationException("No changes found");
        }

        customerDAO.updateCustomerInfo(customer);
    }
}
