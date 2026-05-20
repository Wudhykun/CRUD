package com.user.crud.dto.request

import io.swagger.v3.oas.annotations.media.Schema

data class LoginRequest(
    @Schema(description = "Input username", example = "John Doe")
    val username: String,

    @Schema(description = "Input valid password field", example = "Secret@123")
    val password: String
)