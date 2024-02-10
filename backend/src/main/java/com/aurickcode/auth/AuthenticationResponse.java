package com.aurickcode.auth;

import com.aurickcode.customer.CustomerDTO;

public record AuthenticationResponse(
    String token,
    CustomerDTO customerDTO
) {
}
