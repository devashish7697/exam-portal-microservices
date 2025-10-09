package com.user.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserCredentialDto {

    private long id;
    private String email;
    private String username;
    private String password;
    private Set<String> roles;
}
