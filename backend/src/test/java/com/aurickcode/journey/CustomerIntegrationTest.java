package com.aurickcode.journey;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpHeaders;

import com.aurickcode.customer.Customer;
import com.aurickcode.customer.CustomerDTO;
import com.aurickcode.customer.CustomerRegistrationRequest;
import com.aurickcode.customer.CustomerUpdateRequest;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpHeaders;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.tomcat.util.http.parser.Authorization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest(webEnvironment = RANDOM_PORT) 
public class CustomerIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    private static final Random RANDOM = new Random();
    private static final String CUSTOMER_URI = "/api/v1/customers";

    @Test
    public void canRegisterACustomer() {
        // create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();
        String name = fakerName.fullName();
        String email = fakerName.lastName() +"-" + UUID.randomUUID() + "@aurickcode.com";
        int age = RANDOM.nextInt(1, 100);

        Random random = new Random();
        String password = "password";
        // String[] gender = {"men", "women"};
        // String genVal = gender[0];
        String gender = age % 2 == 0 ? "men" : "women";

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
            name, email, password, age, gender);

        // send a post request
        String jwtToken = webTestClient.post()
            .uri(CUSTOMER_URI)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(request), CustomerRegistrationRequest.class)
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(Void.class)
            .getResponseHeaders()
            .get(HttpHeaders.AUTHORIZATION)
            .get(0);

        // get all customers
        List<CustomerDTO> allCustomers = webTestClient.get()
            .uri(CUSTOMER_URI)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {})
            .returnResult()
            .getResponseBody();

        // make sure that customer is present
        // CustomerDTO expectedCustomer = new Customer(name, email, "password", age, genVal);
        
        Long id = allCustomers.stream()
                            .filter(customer -> customer.email().equals(email))
                            .map(CustomerDTO::id)
                            .findFirst()
                            .orElseThrow();

        CustomerDTO expectedCustomer = new CustomerDTO(
                id, 
                name, 
                email, 
                gender, 
                age, 
                List.of("ROLE_USER"),
                email
            );

        assertThat(allCustomers)
            .contains(expectedCustomer);

        // get customer by id
        webTestClient.get()
            .uri(CUSTOMER_URI + "/{id}", id)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(new ParameterizedTypeReference<CustomerDTO>() {})
            .isEqualTo(expectedCustomer);
    }

    @Test
    void canDeleteCustomer() {
        // create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();
        String name = fakerName.fullName();
        String email = fakerName.lastName() +"-" + UUID.randomUUID() + "@aurickcode.com";
        int age = RANDOM.nextInt(1, 100);

        Random random = new Random();
        String password = "password";
        // String[] gender = {"men", "women"};
        // String genVal = gender[random.nextInt(0, 2)];
        String gender = age % 2 == 0 ? "men" : "women";
        
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
            name, email, password, age, gender);

        CustomerRegistrationRequest request2 = new CustomerRegistrationRequest(
            name, email + ".uk", password, age, gender);

        webTestClient.post()
            .uri(CUSTOMER_URI)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(request), CustomerRegistrationRequest.class)
            .exchange()
            .expectStatus()
            .isOk();

        // send a post request to create customer 2
        String jwtToken = webTestClient.post()
            .uri(CUSTOMER_URI)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(request2), CustomerRegistrationRequest.class)
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(Void.class)
            .getResponseHeaders()
            .get(HttpHeaders.AUTHORIZATION)
            .get(0);

        // get all customers
        List<Customer> allCustomers = webTestClient.get()
            .uri(CUSTOMER_URI)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(new ParameterizedTypeReference<Customer>() {})
            .returnResult()
            .getResponseBody();

        Long id = allCustomers.stream()
                            .filter(customer -> customer.getEmail().equals(email))
                            .map(Customer::getId)
                            .findFirst()
                            .orElseThrow();

        // customer 2 deletes customer 1
        webTestClient.delete()
                .uri(CUSTOMER_URI + "/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        // gcustomer 2 gets customer 1 by id
        webTestClient.get()
            .uri(CUSTOMER_URI + "/{id}", id)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void canUpdateCustomer() {
        // create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();

        String name = fakerName.fullName();
        String email = fakerName.lastName() +"-" + UUID.randomUUID() + "@aurickcode.com";
        int age = RANDOM.nextInt(1, 100);
        String password = "password";
        Random random = new Random();

        // String[] gender = {"men", "women"};
        // String genVal = gender[random.nextInt(0, 2)];
        String gender = age % 2 == 0 ? "men" : "women";

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
            name, email, password, age, gender);

        // send a post request
        String jwtToken = webTestClient.post()
            .uri(CUSTOMER_URI)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(request), CustomerRegistrationRequest.class)
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(Void.class)
            .getResponseHeaders()
            .get(HttpHeaders.AUTHORIZATION)
            .get(0);

        // get all customers
        List<CustomerDTO> allCustomers = webTestClient.get()
            .uri(CUSTOMER_URI)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {})
            .returnResult()
            .getResponseBody();

        Long id = allCustomers.stream()
                            .filter(customer -> customer.email().equals(email))
                            .map(CustomerDTO::id)
                            .findFirst()
                            .orElseThrow();


        String newName = "Ali";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(newName, email, age);

        // update customer
        webTestClient.put()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), CustomerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get customer by id
        CustomerDTO updatedCustomer = webTestClient.get()
            .uri(CUSTOMER_URI + "/{id}", id)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(CustomerDTO.class)
            .returnResult()
            .getResponseBody();
        
            CustomerDTO expected = new CustomerDTO(id, newName, email, gender, age, List.of("ROLE_USER"), email);

        assertThat(updatedCustomer).isEqualTo(expected);
    }
}
