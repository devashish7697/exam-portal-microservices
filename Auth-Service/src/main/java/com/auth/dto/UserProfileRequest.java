package com.auth.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserProfileRequest {

    private long id;
    private String username;
    private String name;
    private String email;
    private String password;
    private Set<String> roles;

}
