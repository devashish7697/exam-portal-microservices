package com.auth.feign;

import com.auth.dto.SignupRequest;
import com.auth.dto.UserProfileRequest;
import com.auth.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "user-service-client", url = "${user.service.url}")
public interface UserClient {

    @PostMapping("api/users/signup")
    UserResponse createUser(UserProfileRequest request);

    @GetMapping("/api/users/{id}")
    UserResponse getById(@PathVariable("id") Long id);

    @GetMapping("/api/users/get")
    List<UserResponse> getAll();

}
