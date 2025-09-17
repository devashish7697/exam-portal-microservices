package com.user.service;

import com.user.dto.RoleUpdateRequest;
import com.user.dto.UserRequest;
import com.user.dto.UserResponse;
import com.user.entites.Role;

import java.util.List;
import java.util.Set;

public interface UserService {
    UserResponse registerOrUpdate(UserRequest request);
    UserResponse getuserById(Long id);
    UserResponse getUserByEmail(String email);
    List<UserResponse> getAllUser();
    UserResponse updateUser(Long id, UserRequest request);
    void deleteUser(Long id);
    UserResponse UpdateRole(Long id, RoleUpdateRequest request);
}
