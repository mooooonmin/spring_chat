package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.common.Constants.Constants;
import com.example.demo.common.exception.Customexception;
import com.example.demo.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Date;

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

    public static String createToken(String name) {
        return JWT.create()
                .withSubject(name)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenTimeForMinute * Constants.ON_MINUTE_TO_MILLIS))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public static String createRefreshToken(String name) {
        return JWT.create()
                .withSubject(name)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenTimeForMinute * Constants.ON_MINUTE_TO_MILLIS))
                .sign(Algorithm.HMAC256(refreshSecretKey));
    }

    public static DecodedJWT checkTokenForRefresh(String token) {

        try {
            DecodedJWT decoded = JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
            log.error("token must be expired : {}", decoded.getSubject());
            throw new Customexception(ErrorCode.ACCESS_TOKEN_IS_NOT_EXPIRED);

        } catch (AlgorithmMismatchException | SignatureVerificationException | InvalidClaimException e) {
            throw new Customexception(ErrorCode.TOKEN_IS_INVALID);

        } catch (TokenExpiredException e) {
            return JWT.decode(token);
        }
    }

    public static DecodedJWT decodedAccessToken(String token) {
        return decodedTokenAfterVerify(token, secretKey);
    }

    public static DecodedJWT decodedRefreshToken(String token) {
        return decodedTokenAfterVerify(token, refreshSecretKey);
    }

    private static DecodedJWT decodedTokenAfterVerify(String token, String key) {

        try {
            return JWT.require(Algorithm.HMAC256(key)).build().verify(token);

        } catch (AlgorithmMismatchException | SignatureVerificationException | InvalidClaimException e) {
            throw new Customexception(ErrorCode.TOKEN_IS_EXPIRED);

        } catch (TokenExpiredException e) {
            throw new Customexception(ErrorCode.TOKEN_IS_EXPIRED);
        }

    }

    public static DecodedJWT decodedJWT(String token) {
        return JWT.decode(token);
    }

    public static String getUserFromToken(String token) {
        DecodedJWT jwt = decodedJWT(token);
        return jwt.getSubject();
    }

}
