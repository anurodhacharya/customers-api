package com.aurickcode.customer;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDAO {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public void deleteCustomerById(Long id) {
        var sql = """
                DELETE FROM customer WHERE id = ?
            """;
        int result = jdbcTemplate.update(sql, id);        
        System.out.println("deleteCustomerById result = " + result);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        String sql = """
                SELECT COUNT(id) FROM customer WHERE email = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsPersonWithId(Long id) {
        String sql = """
                SELECT COUNT(id) FROM customer WHERE id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
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
        
        List<Customer> customers = jdbcTemplate.query(sql, customerRowMapper);
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        String sql = """
                SELECT id, name, email, age FROM Customer WHERE id = ?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, id).stream().findFirst();
    }

    @Override
    public void updateCustomerInfo(Customer update) {
        if(update.getName() != null) {
            String sql = "UPDATE customer SET name = ? WHERE id = ?";
            int result = jdbcTemplate.update(sql, update.getName(), update.getId());
            System.out.println("update customer name result = " + result);
        }

        if(update.getAge() != null) {
            String sql = "UPDATE customer SET age = ? WHERE id = ?";
            int result = jdbcTemplate.update(sql, update.getAge(), update.getId());
            System.out.println("update customer age result = " + result);
        }

        if(update.getEmail() != null) {
            String sql = "UPDATE customer SET email = ? WHERE id = ?";
            int result = jdbcTemplate.update(sql, update.getEmail(), update.getId());
            System.out.println("update customer email result = " + result);
        }

        // String sql = """
        //         UPDATE customer SET name = ?, email = ?, age = ? WHERE id = ?
        //         """;
        // int result = jdbcTemplate.update(sql, update.getName(), update.getEmail(), update.getAge(), update.getId());
        // System.out.println(result);
    }
    
}
