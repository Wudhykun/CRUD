package com.user.crud.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date

@Service
class JwtService {

    @Value("\${app.jwt.secret}")
    private lateinit var secret: String

    @Value("\${app.jwt.expiration-ms}")
    private lateinit var expirationMs: String


    // for login
    fun generateToken(username: String, role: String): String {

        val key = Keys.hmacShaKeyFor(secret.toByteArray())
        return Jwts.builder()
            .subject(username)
            .claim("role", role)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expirationMs.toLong()))
            .signWith(key)
            .compact()
    }

    // for endpoints authentication
    fun extractUsername(token: String): String {

        val key = Keys.hmacShaKeyFor(secret.toByteArray())
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    }

    fun isTokenValid(token: String): Boolean {
        return try {
            val key = Keys.hmacShaKeyFor(secret.toByteArray())
            val expiration = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload
                .expiration
            expiration.after(Date())
        } catch (e: Exception) {
            false
        }
    }
}