package com.user.crud.dto.request

import com.user.crud.model.Role

class AddUserRequest (
    val username: String,
    val email: String,
    val password: String,
    val role: Role,
)