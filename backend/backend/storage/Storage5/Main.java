package com.aurickcode;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.aurickcode.customer.Customer;
import com.aurickcode.customer.CustomerRepository;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
 
    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            Customer alex = new Customer("Alex", "alex@gmail.com", 21);
            Customer jamila = new Customer("Jamila", "jamila@gmail.com", 19);
            
            List<Customer> customers = List.of(alex, jamila);
            customerRepository.saveAll(customers);
        };
    }
}
