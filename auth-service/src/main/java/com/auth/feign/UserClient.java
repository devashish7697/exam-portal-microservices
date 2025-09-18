package com.auth.feign;

import com.auth.dto.UserRequest;
import com.auth.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service-client", url = "${user.service.url}")
public interface UserClient {

    @PostMapping("/api/users/signup")
    UserResponse registerOrUpdate(UserRequest request);

}
