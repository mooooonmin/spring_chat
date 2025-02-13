package com.example.demo.domain.auth.model.response;

import com.example.demo.common.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Login 요청")
public record LoginResponse (

        @Schema(description = "error code")
        ErrorCode errorCode,

        @Schema(description = "jwt token")
        String token

) {}