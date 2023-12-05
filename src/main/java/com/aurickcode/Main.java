package com.aurickcode;

import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
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
            Faker faker = new Faker();
            Name name = faker.name();
            String firstName = name.firstName();
            String lastName = name.lastName();
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@aurickcode.com";
            Random random = new Random();

            Customer customer = new Customer(firstName + " " + lastName, email, random.nextInt(16, 99));

            customerRepository.save(customer);
        };
    }
}
