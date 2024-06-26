package com.jerry.spring.demo.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey

@Service
class TokenService {

    private val secretKey = "iidssjdijdsidjidsjisdjidisdjdsijsdisdjis"  // Change this to a more secure key, need very long!
    private val EXPIRATION_TIME = 900000 // 15 minutes
    private val ROLE_KEY = "role"

    private fun getSigningKey(): SecretKey {
        val keyBytes = secretKey.toByteArray(StandardCharsets.UTF_8)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    suspend fun generateToken(username: String, role: String): String {
        return Jwts.builder()
                .subject(username)
                .claim(ROLE_KEY, role)
                .issuedAt(Date(System.currentTimeMillis()))
                .expiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact()
    }

    fun getUsernameAndRuleFromToken(token: String): Pair<String, String>?{
        return try {
            val claims = geClaimsFromToken(token = token)
            if (claims != null
                    && claims.expiration > Date()
                    && claims.containsKey(ROLE_KEY) ) {
                Pair<String, String>(claims.subject, claims[ROLE_KEY]!!.toString())
            } else {
                null
            }
        } catch (e: Exception){
            null
        }
    }

    fun geClaimsFromToken(token: String): Claims? {
        return try {
            return Jwts.parser().verifyWith(
                getSigningKey()
            ).build().parseSignedClaims(token).payload
        } catch (e: Exception){
            null
        }
    }

}