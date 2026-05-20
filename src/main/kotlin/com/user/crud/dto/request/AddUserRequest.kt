package com.user.crud.dto.request

import com.user.crud.model.Role
import com.user.crud.validator.ValidPassword
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

class AddUserRequest (

    @Schema(description = "Name of the user", example = "John Doe")
    @NotEmpty
    @Size(min = 2, max = 255)
    val username: String,

    @Schema(description = "Email of the user", example = "John.Doe@mail.com")
    @NotEmpty
    @Email
    val email: String,

    @Schema(description = "Input valid password field", example = "Secret@123")
    @ValidPassword
    val password: String,

    @Schema(description = "Set user's role", example = "USER")
    val role: Role,
)