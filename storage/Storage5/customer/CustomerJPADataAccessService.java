package com.aurickcode.customer;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
// import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
// org.hibernate.dialect.PostgreSQLDialect

@Repository("jpa")
public class CustomerJPADataAccessService implements CustomerDAO {

    private final CustomerRepository customerRepository;

    public CustomerJPADataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customerRepository.findById(id);
    }
}
