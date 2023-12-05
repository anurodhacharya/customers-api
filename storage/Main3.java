package com.aurickcode;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController /* This indicates that the class is a controller and that all the methods in the market
                    class will return a JSON response */
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/greet")
    public GreetResponse greet(@RequestParam(value = "name", required = false) String name) {
        String greetMessage = name == null || name.isBlank() ? "Hello" : "Hello " + name;
        GreetResponse response = new GreetResponse(greetMessage, List.of("Java", "Golang", "JavaScript"), new Person("Alex", 28, 30_000));
        return response;
    }

    record Person(String name, int age, double savings) {}

    record GreetResponse(String greet, List<String> favProgrammingLanguages, Person person){}
}
