package com.aurickcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.aurickcode.customer.CustomerController;
import com.aurickcode.customer.CustomerDataAccessService;
import com.aurickcode.customer.CustomerService;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        // CustomerDataAccessService customerDataAccessService = new CustomerDataAccessService();
        // CustomerService customerService = new CustomerService(customerDataAccessService);
        // CustomerController customerController = new CustomerController(customerService);

        SpringApplication.run(Main.class, args);
    }
}
