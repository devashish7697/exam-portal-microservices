package com.user.controller;

import com.user.dto.LoginRequest;
import com.user.dto.UserCredentialDto;
import com.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/internal/users")
public class InternalUserController {

    private final UserService service;

    @PostMapping("/credentials")
    ResponseEntity<UserCredentialDto> getUserCredential(
            @RequestHeader("X-INTERNAL-TOKEN") String internalToken,
            @Valid @RequestBody LoginRequest request
    ){
        try {
            UserCredentialDto dto = service.getUserCredential(request.getUsernameOrEmail(), internalToken);
            return ResponseEntity.ok(dto);
        } catch (SecurityException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
