package com.aurickcode.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDAO {

    private static List<Customer> customers;
    
    static {
        customers = new ArrayList<>();
        Customer alex = new Customer("Alex", "alex@gmail.com", 21);
        customers.add(alex);
        Customer jamila = new Customer("Jamila", "jamila@gmail.com", 19);
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

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customers.stream().anyMatch((c -> c.getEmail().equals(email)));
    }
}
