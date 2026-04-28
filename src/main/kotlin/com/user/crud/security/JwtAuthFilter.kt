package com.user.crud.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtService: JwtService,
    private val customUserDetailsService: CustomUserDetailsService)
    : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authorizationHeader.substring(7)

        if (!jwtService.isTokenValid(token)) {
            filterChain.doFilter(request, response)
            return
        }

        val username = jwtService.extractUsername(token)
        val userDetails = customUserDetailsService.loadUserByUsername(username)
        val authToken = UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.authorities
        )
        SecurityContextHolder.getContext().authentication = authToken
        filterChain.doFilter(request, response)
    }
}