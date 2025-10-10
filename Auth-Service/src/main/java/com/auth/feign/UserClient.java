package com.auth.feign;

import com.auth.dto.SignupRequest;
import com.auth.dto.UserProfileRequest;
import com.auth.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "user-service-client", url = "${user.service.url}")
public interface UserClient {

    @PostMapping("api/users/signup")
    UserResponse createUser( @RequestBody SignupRequest request);

    @GetMapping("/api/users/{id}")
    UserResponse getById(@PathVariable("id") Long id);

    @GetMapping("/api/users/get")
    List<UserResponse> getAll();

    @PostMapping("api/internal/users/credentials")
    UserProfileRequest getUserProfile(
            @RequestHeader("X-INTERNAL-TOKEN") String serviceToken,
            @RequestBody Map<String,String> requestBody
            );

}
