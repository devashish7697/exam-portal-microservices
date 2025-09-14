package com.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "username required")
    @Size(min = 3, max = 30, message = "username length 3..30")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "username must be alphanumeric")
    private String username;

    @NotBlank(message = "email required")
    @Email
    private String email;

    private String name;

    @NotBlank(message = "password required")
    @Size(min = 6, max = 30, message = "Password Must contains atleast 6 charachter ")
    private String password;

}

