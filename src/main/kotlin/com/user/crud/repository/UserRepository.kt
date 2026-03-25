package com.user.crud.repository

import com.user.crud.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    // TODO("Business rule: no duplicate emails")
    fun existsByUsername(username: String): Boolean

    // TODO("check existed user by email")
    fun existsByEmail(email: String): Boolean
}