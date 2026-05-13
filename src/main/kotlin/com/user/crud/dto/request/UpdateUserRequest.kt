package com.user.crud.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

class UpdateUserRequest(
    @NotEmpty
    @Size(min = 2, max = 255)
    val username: String,

    @NotEmpty
    @Email
    val email: String
)