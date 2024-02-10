package com.aurickcode.customer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aurickcode.exception.DuplicateResourceException;
import com.aurickcode.exception.RequestValidationException;
import com.aurickcode.exception.ResourceNotFound;

@Service // we say this so that this class is instantiated for us. // This class's object will be kept in that Application Context
public class CustomerService {
    private final CustomerDAO customerDAO;
    // private final CustomerDAO customerDataAccessService;

    private final PasswordEncoder passwordEncoder;
    private final CustomerDTOMapper customerDTOMapper;

    public CustomerService(@Qualifier("jdbc") CustomerDAO customerDataAccessService,
                            CustomerDTOMapper customerDTOMapper,
                            PasswordEncoder passwordEncoder) {
        this.customerDAO = customerDataAccessService;
        this.customerDTOMapper = customerDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerDAO.selectAllCustomers()
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
    } 

    public CustomerDTO getCustomer(Long id) {
        return customerDAO.selectCustomerById(id)
        .map(customerDTOMapper)
        .orElseThrow(() -> new ResourceNotFound("customer with id [%s] not found".formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        if(customerDAO.existsPersonWithEmail(customerRegistrationRequest.email())) {
            throw new DuplicateResourceException("Email already taken");
        }
        Customer customer = new Customer(
                    customerRegistrationRequest.name(),
                    customerRegistrationRequest.email(),
                    passwordEncoder.encode(customerRegistrationRequest.password()),
                    customerRegistrationRequest.age(),
                    customerRegistrationRequest.gender()
                );
        customerDAO.insertCustomer(customer);
    }

    public void deleteCustomer(Long customerId) {
        if(!customerDAO.existsPersonWithId(customerId)) {
            throw new ResourceNotFound("person does not exist");
        }
        customerDAO.deleteCustomerById(customerId);
    }

    public void updateCustomerInfo(Long id, CustomerUpdateRequest updateRequest) {
        Customer customer = customerDAO.selectCustomerById(id)
        .orElseThrow(() -> new ResourceNotFound("customer with id [%s] not found".formatted(id)));

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
