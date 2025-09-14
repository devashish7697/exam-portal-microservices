package com.user.service;

import com.user.dto.RoleUpdateRequest;
import com.user.dto.UserRequest;
import com.user.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    UserResponse getuserById(Long id);
    UserResponse getUserByUsername(String username);
    List<UserResponse> getAllUser();
    UserResponse updateUser(Long id, UserRequest request);
    void deleteUser(Long id);
    void UpdateRole(Long id, RoleUpdateRequest request);
}
