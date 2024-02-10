package com.aurickcode.journey;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;

import com.aurickcode.auth.AuthenticationRequest;
import com.aurickcode.auth.AuthenticationResponse;
import com.aurickcode.customer.Customer;
import com.aurickcode.customer.CustomerDTO;
import com.aurickcode.customer.CustomerRegistrationRequest;
import com.aurickcode.customer.CustomerUpdateRequest;
import com.aurickcode.jwt.JWTUtil;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpHeaders;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AuthenticationIT {
    
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private JWTUtil jwtUtil;

    private static final Random RANDOM = new Random();
    private static final String AUTHENTICATION_PATH = "/api/v1/auth";
    private static final String CUSTOMER_URI = "/api/v1/customers";

    @Test
    public void canLogin() {
        // create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();
        String name = fakerName.fullName();
        String email = fakerName.lastName() +"-" + UUID.randomUUID() + "@aurickcode.com";
        int age = RANDOM.nextInt(1, 100);

        Random random = new Random();
        // String password = "password";
        // String[] gender = {"men", "women"};
        String gender = age % 2 == 0 ? "men" : "women";

        String password = "password";

        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest(
            name, email, password, age, gender);

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);

        webTestClient.post()
            .uri(AUTHENTICATION_PATH + "/login")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
            .exchange()
            .expectStatus()
            .isUnauthorized();

        // send a post request
        webTestClient.post()
            .uri(CUSTOMER_URI)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                Mono.just(customerRegistrationRequest), 
                CustomerRegistrationRequest.class
            )
            .exchange()
            .expectStatus()
            .isOk();

        EntityExchangeResult<AuthenticationResponse> result = webTestClient.post()
            .uri(AUTHENTICATION_PATH + "/login")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(new ParameterizedTypeReference<AuthenticationResponse>() {
            }).returnResult();

        String jwtToken = result.getResponseHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

        AuthenticationResponse authenticationResponse = result.getResponseBody();

        CustomerDTO customerDTO = authenticationResponse.customerDTO();

        assertThat(jwtUtil.isTokenValid(jwtToken, customerDTO.username()));

        assertThat(customerDTO.email()).isEqualTo(email);
        assertThat(customerDTO.age()).isEqualTo(age);
        assertThat(customerDTO.name()).isEqualTo(name);
        assertThat(customerDTO.username()).isEqualTo(email);
        assertThat(customerDTO.Gender()).isEqualTo(gender);
        assertThat(customerDTO.roles()).isEqualTo(List.of("ROLE_USER"));
    }
}
