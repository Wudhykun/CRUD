package com.user.crud.dto.response

import com.user.crud.model.Job
import com.user.crud.model.Role

data class AddUserResponse(
    val id: Long,
    val username: String,
    val email: String,
    val role: Role,
)