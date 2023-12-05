package com.aurickcode.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDataAccessService implements CustomerDAO {

    private static List<Customer> customers;
    
    static {
        customers = new ArrayList<>();
        Customer alex = new Customer(1, "Alex", "alex@gmail.com", 21);
        customers.add(alex);
        Customer jamila = new Customer(2, "Jamila", "jamila@gmail.com", 19);
        customers.add(jamila);
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customers.stream()
        .filter(c -> c.getId().equals(id))
        .findFirst();
    }
}
