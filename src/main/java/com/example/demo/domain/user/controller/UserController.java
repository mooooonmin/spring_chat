package com.example.demo.domain.user.controller;

import com.example.demo.domain.user.model.response.UserSearchResponse;
import com.example.demo.domain.user.sevice.UserServiceV1;
import com.example.demo.security.JWTProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API", description = "V1 User API")
@RestController
@RequestMapping("/api/v1/User")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceV1 userService;
    private final UserServiceV1 userServiceV1;

    public UserSearchResponse searchUser(
            @PathVariable("name") String name,
            @RequestHeader("Authorization") String authString
    ) {

        String token = JWTProvider.extractToken(authString);
        String user = JWTProvider.getUserFromToken(token);

        return userServiceV1.searchUser(name, user);
    }

}
