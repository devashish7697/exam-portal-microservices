package com.user.service;

import com.user.dto.RoleUpdateRequest;
import com.user.dto.UserRequest;
import com.user.entites.Role;
import com.user.entites.User;
import com.user.dto.UserResponse;
import com.user.exception.ResourceNotFoundException;
import com.user.exception.UserException;
import com.user.repository.UserRepository;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Service
public class UserServiceImplements implements UserService{

    private final UserRepository repo;
    private final ModelMapper modelMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse registerOrUpdate(UserRequest request) {

       User user = repo.findByProviderAndProviderId(request.getProvider(), request.getProviderId())
               .orElse(new User());

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setProvider(request.getProvider());
        user.setProviderId(request.getProviderId());

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(new HashSet<>(Set.of(Role.ROLE_USER)));
        } else {
            // Ensure ROLE_USER is always present
            user.getRoles().add(Role.ROLE_USER);
        }

        User savedUser = repo.save(user);
        return modelMapper.map(savedUser, UserResponse.class);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getuserById(Long id) {
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
        if (repo.existsById(id)){
            User user = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

            if (request.getProvider() != null && !request.getProvider().isBlank() &&
                    request.getProviderId() != null && !request.getProviderId().isBlank()) {

                Optional<User> existing = repo.findByProviderAndProviderId(
                        request.getProvider(), request.getProviderId());

                if (existing.isPresent() && !existing.get().getId().equals(id)) {
                    throw new IllegalArgumentException(
                            "Another user already exists with provider "
                                    + request.getProvider() + " and providerId "
                                    + request.getProviderId()
                    );
                }

                user.setProvider(request.getProvider());
                user.setProviderId(request.getProviderId());
            }

            // check if email field not null or empty
            if (request.getEmail() != null && !request.getEmail().isBlank()){
              user.setEmail(request.getEmail());
            }

            if (request.getName() != null && !request.getName().isBlank()){
                user.setName(request.getName());
            }

            User updated = repo.save(user);
            return modelMapper.map(updated, UserResponse.class);

        }else {
            throw new IllegalArgumentException("User Not found With id : "+id);
        }
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
        if (repo.existsById(id)){
            User user = repo.findById(id).
                    orElseThrow(() -> new UserException("User not found with id : "+id));
            user.setRoles(request.getRoles());
           User saved = repo.save(user);
           return modelMapper.map(saved, UserResponse.class);
        } else {
            throw new UserException("user nor found with id : "+id);
        }
    }
}
