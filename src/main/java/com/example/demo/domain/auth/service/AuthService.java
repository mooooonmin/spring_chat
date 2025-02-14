package com.example.demo.domain.auth.service;

import com.example.demo.common.exception.Customexception;
import com.example.demo.common.exception.ErrorCode;
import com.example.demo.domain.auth.model.request.CreateUserRequest;
import com.example.demo.domain.auth.model.request.LoginRequest;
import com.example.demo.domain.auth.model.response.CreateUserResponse;
import com.example.demo.domain.auth.model.response.LoginResponse;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.domain.repository.entity.User;
import com.example.demo.domain.repository.entity.UserCredentials;
import com.example.demo.security.Hasher;
import com.example.demo.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final Hasher hasher;

    @Transactional(transactionManager = "createUserTransactionManager") // 커스텀한 트랜잭션매니저 사용
    public CreateUserResponse createUser(CreateUserRequest request) {

        Optional<User> user = userRepository.findByName(request.name());

        if (user.isPresent()) {
            log.error("USER_ALREADY_EXISTS: {}", request.name());
            throw new Customexception(ErrorCode.USER_ALREADY_EXISTS);
        }

        try {

            User newUser = this.newUser(request.name());
            UserCredentials newCredentials = this.newUserCredentials(request.password(), newUser);
            newUser.setCredentials(newCredentials);

            User savedUser = this.userRepository.save(newUser);

            if (savedUser == null) {
                log.error("USER_SAVED_FAILED: {}", request.name());
                throw new Customexception(ErrorCode.USER_SAVED_FAILED);
            }

        } catch (Exception e) {
            throw new Customexception(ErrorCode.USER_SAVED_FAILED);
        }

        return new CreateUserResponse(request.name());
    }

    public LoginResponse login(LoginRequest request) {

        Optional<User> user = userRepository.findByName(request.name());

        if (!user.isPresent()) {
            log.error("NOT_EXIST_USER: {}", request.name());
            throw new Customexception(ErrorCode.NOT_EXIST_USER);
        }

        user.map(u -> {
            String hashedValue = hasher.getHashingValue(request.password());

            if (!u.getUserCredentials().getHashed_password().equals(hashedValue)) {
                throw new Customexception(ErrorCode.MISS_MATCH_PASSWORD);
            }

            return hashedValue;

        }).orElseThrow(() -> {
            throw new Customexception(ErrorCode.MISS_MATCH_PASSWORD);
        });

        String token = JWTProvider.createRefreshToken(request.name());
        return new LoginResponse(ErrorCode.SUCCESS, "Token");
    }

    public String getUserFromToken(String token) {
        return JWTProvider.getUserFromToken(token);
    }

    private User newUser(String name) {
        User newUser = User
                .builder()
                .name(name)
                .created_at(new Timestamp(System.currentTimeMillis()))
                .build();

        return newUser;
    }

    // 패스워드 hashing
    private UserCredentials newUserCredentials(String password, User user) {
        String hashedValie = hasher.getHashingValue(password);

        UserCredentials cre = UserCredentials
                .builder()
                .user(user)
                .hashed_password(hashedValie)
                .build();

        return cre;
    }

}
