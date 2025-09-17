package com.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank
    private String provider;

    @NotBlank
    private String providerId;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

}

