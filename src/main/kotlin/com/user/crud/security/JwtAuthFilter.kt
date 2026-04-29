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

        // TODO("Replace with `getUserAuth`")
        /**
         * val userAuth = jwtTokenService.getUserAuth(token)
         * val authorities = userAuth.roles.map { SimpleGrantedAuthority("ROLE_$it") }
         * val authentication = UsernamePasswordAuthenticationToken(userAuth, null, authorities)
         * SecurityContextHolder.getContext().authentication = authentication
         *
         * By removing `AuthenticationManager` in login and using jwtFilter,
         * then we don't need to implement the `UserDetailsService` to loadUserByUsername
         *
         * Replace with `getUserAuth`
         * Pros: This will decouple from calling db every request, fully trust JWT, high throughput
         * Cons:
         * - Roles become stale until token expires
         * - Cannot react immediately to:
         *      - role changes
         *      - account disable/lock
         * */
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