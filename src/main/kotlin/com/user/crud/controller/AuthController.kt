package com.user.crud.controller

import com.user.crud.dto.request.AddUserRequest
import com.user.crud.dto.request.LoginRequest
import com.user.crud.dto.response.LoginResponse
import com.user.crud.exception.UserNotFoundException
import com.user.crud.repository.UserRepository
import com.user.crud.security.JwtService
import com.user.crud.service.UserService
import jakarta.validation.Valid
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val userService: UserService,
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): LoginResponse {

        val user = userRepository.findByUsername(request.username)
            ?: throw UserNotFoundException("User ${request.username} not found")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw BadCredentialsException("Invalid password")
        }

        val userAuth = JwtService.UserAuth(userId = user.id, roles = setOf(user.role.name))

        val token = jwtService.generateToken(user.username, userAuth)

        return LoginResponse(token)
    }

    @PostMapping("/register")
    fun addUser(@RequestBody @Valid request: AddUserRequest) = userService.addUser(request)
}