package com.aurickcode.auth;

public record AuthenticationRequest(
    String username,
    String password
) {
    
}
