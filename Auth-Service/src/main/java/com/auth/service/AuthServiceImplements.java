package com.auth.service;

import com.auth.dto.*;
import com.auth.exception.FeignClientException;
import com.auth.exception.InvalidCredentialsException;
import com.auth.exception.ServiceUnavailableException;
import com.auth.exception.UserAlreadyExistsException;
import com.auth.feign.UserClient;
import com.auth.security.JwtUtil;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImplements implements AuthService{

    private final UserClient client;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("${internal.auth.token}")
    private String internalToken;

    @Override
    public UserResponse signup(SignupRequest request) {
        try {
            UserResponse created = client.createUser(request);
            if (created == null) {
                throw new FeignClientException("User service returned null on signup");
            }
            return created;
        } catch (FeignException.Conflict ex) {
            throw new UserAlreadyExistsException("Username or email already in use");
        } catch (FeignException.BadRequest ex) {
            throw new FeignClientException("Validation error: " + ex.contentUTF8());
        } catch (FeignException.ServiceUnavailable ex) {
            throw new ServiceUnavailableException("User service unavailable");
        } catch (FeignException ex) {
            throw new FeignClientException("User service error: " + ex.status() + " - " + ex.getMessage());
        }
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        try {
            //Making internal call to user-service and fetching user data
            Map<String,String> body = Map.of("usernameOrEmail",request.getUsernameOrEmail());
            UserProfileRequest profileRequest = client.getUserProfile(internalToken, body);
            if (profileRequest == null){
                throw new InvalidCredentialsException("Invalid username/email or password");
            }

            boolean ok = passwordEncoder.matches(request.getPassword(), profileRequest.getPassword());
            if (!ok) {
                throw new InvalidCredentialsException("Invalid username/email or password");
            }
            Set<String> roles = profileRequest.getRoles()==null ? Set.of() : profileRequest.getRoles();
            // generating token
            String token = jwtUtil.generateToken(profileRequest.getId(),profileRequest.getUsername(),
                    profileRequest.getEmail(), profileRequest.getName(), roles);

            UserResponse response = new UserResponse();
            response.setId(profileRequest.getId());
            response.setEmail(profileRequest.getEmail());
            response.setUsername(profileRequest.getUsername());
            response.setName(profileRequest.getName());
            response.setRoles(roles);

            return new AuthResponse(token,response);
        } catch (FeignException.NotFound ex) {
            throw new InvalidCredentialsException("User not found");
        } catch (FeignException.ServiceUnavailable ex) {
            throw new ServiceUnavailableException("User service unavailable");
        } catch (FeignException ex) {
            throw new FeignClientException("User service error: " + ex.status() + " - " + ex.getMessage());
        }
    }
}
