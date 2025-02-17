package com.example.demo.domain.auth.controller;

import com.example.demo.domain.auth.model.request.CreateUserRequest;
import com.example.demo.domain.auth.model.request.LoginRequest;
import com.example.demo.domain.auth.model.response.CreateUserResponse;
import com.example.demo.domain.auth.model.response.LoginResponse;
import com.example.demo.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth API", description = "V1 Auth API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthcontrollerV1 {

    private final AuthService authService;

    @Operation(
            summary = "새로운 유저를 생성.",
            description = "새로운 유저 생성"
    )
    @PostMapping("/create-user")
    public CreateUserResponse createUser(@RequestBody @Valid CreateUserRequest request) {
        return authService.createUser(request);
    }

    @Operation(
            summary = "로그인 처리",
            description = "로그인 진행"
    )
    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    @Operation(
            summary = "get user name",
            description = "token 기반으로 user 가져옴"
    )
    @GetMapping("/verify-token/{token}")
    public String getUserFromToken(@PathVariable("toekn") String token) {
        return authService.getUserFromToken(token);
    }

}
