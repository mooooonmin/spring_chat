package com.example.demo.domain.auth.service;

import com.example.demo.domain.auth.model.request.CreateUserRequest;
import com.example.demo.domain.auth.model.response.CreateUserResponse;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.domain.repository.entity.User;
import com.example.demo.domain.repository.entity.UserCredentials;
import com.example.demo.security.Hasher;
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
            // TODO 에러
        }

        try {

            User newUser = this.newUser(request.name());
            UserCredentials newCredentials = this.newUserCredentials(request.password(), newUser);
            newUser.setCredentials(newCredentials);

            User savedUser = this.userRepository.save(newUser);

            if (savedUser == null) {
                // TODO 에러처리
            }

        } catch (Exception e) {

            // TODO 에러

        }

        return new CreateUserResponse(request.name());
    }

    private User newUser(String name) {
        User newUser = User.builder()
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
