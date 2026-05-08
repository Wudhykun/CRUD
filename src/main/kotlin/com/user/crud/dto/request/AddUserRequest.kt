package com.user.crud.dto.request

import com.user.crud.model.Role
import com.user.crud.validator.ValidPassword
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

class AddUserRequest (
    @NotEmpty
    @Size(min = 2, max = 255)
    val username: String,

    @NotEmpty
    @Email
    val email: String,

    @ValidPassword
    val password: String,

    val role: Role,
)