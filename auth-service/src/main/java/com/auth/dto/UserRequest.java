package com.auth.dto;

import lombok.Data;

@Data
public class UserRequest {

    private String provider;
    private String providerId;
    private String email;
    private String name;



}
