package com.example.demo.domain.auth.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "User를 생성합니다.")
public record CreateUserRequest (

        @Schema(description = "유저 이름")
        @NotNull
        @NotBlank
        String name,

        @Schema(description = "유저 비밀번호")
        @NotNull
        @NotBlank
        String password

) {

}