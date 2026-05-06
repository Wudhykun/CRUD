package com.user.crud.security

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date

@Service
class JwtService(

    @Value("\${app.jwt.secret}")
    private val secret: String,

    @Value("\${app.jwt.expiration-ms}")
    private val expirationMs: String

) {

    private val objectMapper = ObjectMapper().registerModules(KotlinModule.Builder().build())

    private val signingKey =
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class UserAuth(
        var userId: Long? = null,
        var roles: Set<String>
    )

    fun generateToken(username: String, user: UserAuth): String {
        return Jwts.builder()
            .subject(username)
            .claim("payload", user)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expirationMs.toLong()))
            .signWith(signingKey)
            .compact()
    }

    fun getUserAuth(token: String): UserAuth {
        val claimsPayload = Jwts.parser().verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .payload["payload"]
        return objectMapper.convertValue(claimsPayload, UserAuth::class.java)
    }

    fun extractUsername(token: String): String {
        return Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    }

    fun isTokenValid(token: String): Boolean {
        return try {
            val expiration = Jwts.parser()
                .verifyWith(signingKey)
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