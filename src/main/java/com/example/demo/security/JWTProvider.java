package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JWTProvider {

    private static String secretKey;
    private static String refreshSecretKey;
    private static long tokenTimeForMinute;
    private static long refreshTokenTimeForMinute;

    @Value("${token.secret-key}")
    public void setSecretKey(String secretKey) {
        JWTProvider.secretKey = secretKey;
    }

    @Value("${token.refresh-secret-key}")
    public void setRefreshSecretKey(String refreshSecretKey) {
        JWTProvider.refreshSecretKey = refreshSecretKey;
    }

    @Value("${token.token-time}")
    public void setTokenTimeForMinute(long tokenTimeForMinute) {
        JWTProvider.tokenTimeForMinute = tokenTimeForMinute;
    }

    @Value("${token.refresh-token-time}")
    public void setRefreshTokenTimeForMinute(long refreshTokenTimeForMinute) {
        JWTProvider.refreshTokenTimeForMinute = refreshTokenTimeForMinute;
    }

}
