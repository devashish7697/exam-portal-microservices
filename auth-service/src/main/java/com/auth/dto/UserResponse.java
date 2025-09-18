package com.auth.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserResponse {

    private Long id;
    private String provider;
    private String providerId;
    private String email;
    private String name;
    private Set<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
