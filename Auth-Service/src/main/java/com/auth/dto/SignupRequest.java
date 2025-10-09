package com.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[a-z][a-z0-9]*$", message = "Invalid username format, username must starts with alphabet and contains numeric values")
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 6,  message = "Password must be at least 6 characters long")
    private String password;

}

