package com.aurickcode;

import java.util.Random;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    CommandLineRunner runner(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Faker faker = new Faker();
            Name name = faker.name();
            String firstName = name.firstName();
            String lastName = name.lastName();
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@aurickcode.com";
            Random random = new Random();

            String[] gender = {"Male", "Female"};

            Customer customer = new Customer(firstName + " " + lastName, email, passwordEncoder.encode(UUID.randomUUID().toString()), random.nextInt(16, 99), gender[random.nextInt(0, 2)]);

            customerRepository.save(customer);
        };
    }
}
