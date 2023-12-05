package com.aurickcode.Storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController /* This means that any method within this class that has any of that '@GetMapping' annotations,
                 they will be exposed as REST endpoints that clients can call. what is apache tomcat */
public class Main1 {
    public static void main(String[] args) {
        SpringApplication.run(Main1.class, args);
    }

    // @GetMapping("/greet") // This is your first API with springboot.
    // public String greet() {
    //     return "Hello";
    // }

    @GetMapping("/greet") // This is your first API with springboot.
    public GreetResponse greet() {
        return new GreetResponse("Hello");
    }

    record GreetResponse(String greet){}
}
