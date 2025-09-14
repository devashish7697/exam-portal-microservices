package com.user.dto;

import com.user.entites.Role;
import lombok.Data;

import java.time.Instant;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String username;
    private String name;
    private Role role;
    private Instant createdAt;
    private Instant updatedAt;
}
