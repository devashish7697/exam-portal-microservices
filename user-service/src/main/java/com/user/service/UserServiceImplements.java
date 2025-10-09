package com.user.service;

import com.user.dto.RoleUpdateRequest;
import com.user.dto.UserCredentialDto;
import com.user.dto.UserRequest;
import com.user.entites.Role;
import com.user.entites.User;
import com.user.dto.UserResponse;
import com.user.exception.ResourceNotFoundException;
import com.user.exception.UserException;
import com.user.repository.UserRepository;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Service
public class UserServiceImplements implements UserService{

    private final UserRepository repo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Value("${internal.service.token}")
    private String serviceToken;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse createUSer(UserRequest request) {

        if (!repo.existsByUsername(request.getUsername()) && !repo.existsByEmail(request.getEmail())){
            User user = new User();

            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setName(request.getName());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRoles(Set.of(Role.ROLE_USER));



            User savedUser = repo.save(user);
            return modelMapper.map(savedUser, UserResponse.class);
        }
        else {
            throw new UserException("User Already exists");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getUserById(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not Found with id : "+id));
        return modelMapper.map(user, UserResponse.class);
    }


    @Override
    public UserResponse getUserByEmail(String email) {
        User user =  repo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not Found with username : "+email));
        return modelMapper.map(user, UserResponse.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponse> getAllUser() {
        return repo.findAll().stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse updateUser(Long id, UserRequest request) {

            User user = repo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            if (!user.getEmail().equals(request.getEmail()) && repo.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
            if (!user.getUsername().equals(request.getUsername()) && repo.existsByUsername(request.getUsername())) {
                throw new IllegalArgumentException("Username already exists");
            }

                user.setName(request.getName());
                user.setEmail(request.getEmail());
                user.setUsername(request.getUsername());
                user.setPassword(passwordEncoder.encode(request.getPassword()));

            User updated = repo.save(user);
            return modelMapper.map(updated, UserResponse.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(Long id) {
        if (!repo.existsById(id)){
            throw new UserException("user not found with id : "+id);
        }
        repo.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse UpdateRole(Long id, RoleUpdateRequest request) {
            User user = repo.findById(id).
                    orElseThrow(() -> new UserException("User not found with id : "+id));
            user.setRoles(request.getRoles());
           User saved = repo.save(user);

           return modelMapper.map(saved, UserResponse.class);
    }

    @Override
    public List<UserResponse> searchUserByUsername(String username) {
        List<User> users = repo.searchUserByUsername(username);
        List<UserResponse> result = users.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public UserCredentialDto getUserCredential(String usernameOrEmail, String serviceToken) {
        //verify internal call token
        if (!serviceToken.equals(serviceToken)){
            throw new SecurityException("Unauthorized Internal Service Call");
        }
        Optional<User> optUser = repo.findByUsername(usernameOrEmail);
        if (optUser.isEmpty()){
            optUser = repo.findByEmail(usernameOrEmail);
        }

        User user = optUser.orElseThrow(() -> new RuntimeException("User not Found with email or username : "+usernameOrEmail));

        UserCredentialDto userCredentialDto = new UserCredentialDto();
        userCredentialDto.setId(user.getId());
        userCredentialDto.setUsername(user.getUsername());
        userCredentialDto.setEmail(user.getEmail());
        userCredentialDto.setPassword(user.getPassword());
        userCredentialDto.setRoles(user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()));
        return userCredentialDto;
    }
}
