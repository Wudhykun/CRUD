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
        val auth = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )

        val token = jwtService.generateToken(auth.name, auth.authorities.first().authority ?: "ROLE_USER")
        return LoginResponse(token)
    }
}