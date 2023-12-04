package com.aurickcode;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController /* This means that any method within this class that has any of that '@GetMapping' annotations,
                * they will be exposed as REST endpoints that clients can call. what is apache tomcat */
public class Main2 {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/greet") // This is your first API with springboot.
    public GreetResponse greet() {
        GreetResponse response = new GreetResponse("Hello", List.of("Java", "Golang", "JavaScript"), new Person("Alex", 28, 30_000));
        return response;
    }

    record Person(String name, int age, double savings) {}

    record GreetResponse(String greet, List<String> favProgrammingLanguages, Person person){}
}
