package com.example.demo.domain.user.model.response;

import com.example.demo.common.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "User 검색 리스트")
public record UserSearchResponse (

        @Schema(description = "error code")
        ErrorCode description,

        @Schema(description = "이름")
        List<String> name

) {}