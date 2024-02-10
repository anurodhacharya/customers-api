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
        Customer alex = new Customer("Alex", "alex@gmail.com", "password", 21, "men");
        customers.add(alex);
        Customer jamila = new Customer("Jamila", "jamila@gmail.com",  "password", 19, "men");
        customers.add(jamila);
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
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

    @Override
    public boolean existsPersonWithId(Long id) {
        return customers.stream().anyMatch((c -> c.getEmail().equals(id)));
    }

    @Override
    public void deleteCustomerById(Long id) {
        // customers.remove(id);
        customers.stream()
            .filter(c -> c.getId().equals(id))
            .findFirst()
            .ifPresent(c -> customers.remove(c));
    }

    @Override
    public void updateCustomerInfo(Customer customer) {
        customers.add(customer);
    }

    @Override
    public Optional<Customer> selectUserByEmail(String email) {
        return customers.stream()
            .filter(c -> c.getUsername().equals(email))
            .findFirst();
    }

}
