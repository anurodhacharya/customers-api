package com.aurickcode.customer;

import java.util.List;
import java.util.Optional;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDAO {

    private final JdbcTemplate jdbcTemplate;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void deleteCustomerById(Integer id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(name, email, age) VALUES (?, ?, ?)
            """;
        int result = jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge());
        System.out.println("Result: " + result);
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name, email, age FROM customer
            """;
        RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {
            Customer customer = new Customer(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("age")
            );
        return customer;
        };
        List<Customer> customers = jdbcTemplate.query(sql, customerRowMapper);
        
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public void updateCustomerInfo(Customer update) {
        // TODO Auto-generated method stub
        
    }
    
}
