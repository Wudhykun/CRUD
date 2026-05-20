package com.user.crud.dto.response

import io.swagger.v3.oas.annotations.media.Schema

data class LoginResponse(
    @Schema(description = "JWT bearer token", example = "eyJhbGci...")
    val token: String
)