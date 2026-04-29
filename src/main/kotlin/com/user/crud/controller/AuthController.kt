package com.user.crud.controller

import com.user.crud.dto.request.LoginRequest
import com.user.crud.dto.response.LoginResponse
import com.user.crud.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authenticationManager: AuthenticationManager, private  val jwtService: JwtService) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): LoginResponse {
        // TODO("Let's not use AuthenticationManager")
        /**
         * 1. check user existed by username through UserRepository, throw error if not not found
         * 2. use BCryptPasswordEncoder to validate password is match, throw error if not matched
         * 3. existed user with matched password, generate token and response
         * By removing AuthenticationManager:
         *      - full control
         *      - no framework dependency
         * */
        val auth = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )

        val token = jwtService.generateToken(auth.name, auth.authorities.first().authority ?: "ROLE_USER")
        return LoginResponse(token)
    }
}