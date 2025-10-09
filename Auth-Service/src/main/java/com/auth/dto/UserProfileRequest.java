package com.auth.dto;

import lombok.Data;

@Data
public class UserProfileRequest {

    private String username;
    private String name;
    private String email;
    private String password;

}
