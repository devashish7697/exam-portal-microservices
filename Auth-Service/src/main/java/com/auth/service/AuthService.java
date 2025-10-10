package com.auth.service;

import com.auth.dto.AuthResponse;
import com.auth.dto.LoginRequest;
import com.auth.dto.SignupRequest;
import com.auth.dto.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    UserResponse signup(SignupRequest request);
    AuthResponse login(LoginRequest request);

}
