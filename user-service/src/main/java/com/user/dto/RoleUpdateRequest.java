package com.user.dto;

import com.user.entites.Role;
import lombok.Data;
import lombok.Setter;

import java.util.Set;

@Data
public class RoleUpdateRequest {
    private Set<Role> roles;
}
