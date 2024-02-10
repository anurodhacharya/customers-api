package com.aurickcode.customer;

import java.util.List;

public record CustomerDTO(
    Long id, 
    String name, 
    String email, 
    String Gender, 
    Integer age, 
    List<String> roles, 
    String username
) {
    
}
