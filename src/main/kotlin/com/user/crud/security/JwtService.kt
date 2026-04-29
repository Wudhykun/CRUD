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

    // TODO("Object Mapper initializer")
    /** Dependencies KotlinModule
     *  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
     * */
//    private val objectMapper = ObjectMapper().registerModules(KotlinModule.Builder().build())

    // TODO("initialized one signingKey")
//    private val signingKey =
//        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))

    // for login
    // TODO("Embed `UserAuth` to claim instead of just role")
    /**
     * @JsonIgnoreProperties(ignoreUnknown = true)
     * data class UserAuth(
     *     var userId: Long? = null,
     *     var roles: Set<String>
     * )
     *      fun generateToken(username: String, user: UserAuth) {
     *          return Jwts.builder()
     *             .subject(username)
     *             .claim("payload", user)
     *             .issuedAt(Date())
     *             .expiration(Date(System.currentTimeMillis() + expirationMs.toLong()))
     *             .signWith(signingKey)
     *             .compact()
     *      }
     * */
    fun generateToken(username: String, role: String): String {
        // TODO("remove and use `signingKey`")
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
    //TODO("Get UserAuth from claim instead")
    /**
     *  fun getUserAuth(token: String): UserAuth {
     *         val claimsPayload = Jwts.parser().verifyWith(signingKey())
     *             .build()
     *             .parseSignedClaims(token)
     *             .payload["payload"]
     *         val auth = objectMapper.convertValue(claimsPayload, UserAuth::class.java)
     *         return auth
     *     }
     * */
    fun extractUsername(token: String): String {
        // TODO("remove and use `signingKey`")
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
            // TODO("remove and use `signingKey`")
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