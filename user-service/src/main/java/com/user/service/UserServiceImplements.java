package com.user.service;

import com.user.dto.RoleUpdateRequest;
import com.user.dto.UserRequest;
import com.user.entites.Role;
import com.user.entites.User;
import com.user.dto.UserResponse;
import com.user.exception.ResourceNotFoundException;
import com.user.repository.UserRepository;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Data
@Service
public class UserServiceImplements implements UserService{

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse createUser(UserRequest request) {

        // check before user creation
        if (repo.existsByUsername(request.getUsername())){
            throw new IllegalArgumentException("Username already Exists");
        }
        if (repo.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("Email already Exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Role.ROLE_USER);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        User savedUser = repo.save(user);
        return toResponse(savedUser);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getuserById(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not Found with id : "+id));
        return toResponse(user);
    }


    @Override
    public UserResponse getUserByUsername(String username) {
        return repo.findByUsername(username).map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("User not Found with username : "+username));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponse> getAllUser() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        if (repo.existsById(id)){
            User user = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

            //check for username already exists
            if (request.getUsername() != null && !request.getUsername().isBlank()){
                if (!request.getUsername().equals(user.getUsername()) && repo.existsByUsername(request.getUsername())){
                    throw new IllegalArgumentException("Username already exists, try setting different username");
                }
                user.setUsername(request.getUsername());
            }

            // check if email field not null or empty
            if (request.getEmail() != null && !request.getEmail().isBlank()){
              user.setEmail(request.getEmail());
            }

            if (request.getPassword() != null && !request.getPassword().isBlank()){
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }

            if (request.getName() != null && !request.getName().isBlank()){
                user.setName(request.getName());
            }

            user.setUpdatedAt(Instant.now());
            User updated = repo.save(user);
            return toResponse(updated);

        }else {
            throw new IllegalArgumentException("User Not found With id : "+id);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(Long id) {
        if (!repo.existsById(id)){
            repo.findById(id).orElseThrow(() -> new IllegalArgumentException("user nor found with id : "+id));
        }
        repo.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void UpdateRole(Long id, RoleUpdateRequest request) {
        if (repo.existsById(id)){
            User user = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not Found with id : "+id));
            if (request.getRole() != null){
                user.setRoles(request.getRole());
                user.setUpdatedAt(Instant.now());
                repo.save(user);
            }
        } else {
            repo.findById(id).orElseThrow(() -> new IllegalArgumentException("user nor found with id : "+id));
        }
    }

    private UserResponse toResponse(User u) {
        UserResponse r = new UserResponse();
        r.setUsername(u.getUsername());
        r.setEmail(u.getEmail());
        r.setName(u.getName());
        r.setId(u.getId());
        r.setRole(u.getRoles());
        r.setCreatedAt(u.getCreatedAt());
        r.setUpdatedAt(u.getUpdatedAt());

        return r;
    }
}
