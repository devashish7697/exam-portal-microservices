package com.user.service;

import com.user.dto.RoleUpdateRequest;
import com.user.dto.UserCredentialDto;
import com.user.dto.UserRequest;
import com.user.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUSer(UserRequest request);
    UserResponse getUserById(Long id);
    UserResponse getUserByEmail(String email);
    List<UserResponse> getAllUser();
    UserResponse updateUser(Long id, UserRequest request);
    void deleteUser(Long id);
    UserResponse UpdateRole(Long id, RoleUpdateRequest request);
    List<UserResponse> searchUserByUsername(String username);

    // for internal call by auth-service
    UserCredentialDto getUserCredential(String usernameOrEmail, String serviceToken);
}
