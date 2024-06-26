package com.jerry.spring.demo.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.oauth2.core.OAuth2AuthorizationException
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class SecurityContextRepository: ServerSecurityContextRepository {

    @Autowired
    lateinit var tokenService: TokenService

    override fun save(exchange: ServerWebExchange, context: SecurityContext?): Mono<Void> {
        throw UnsupportedOperationException("Save method not supported")
    }

    override fun load(serverWebExchange: ServerWebExchange): Mono<SecurityContext> {
        val authHeader = serverWebExchange.request.headers.getFirst("Authorization")
        return if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val authToken = authHeader.substring(7)
            val data = tokenService.getUsernameAndRuleFromToken(authToken)
            if (data != null) {
                val auth = UsernamePasswordAuthenticationToken(data.first, null, listOf(
                    SimpleGrantedAuthority(data.second)
                ))
                Mono.just(SecurityContextImpl(auth))
            } else {
                Mono.empty()
            }

        } else {
            Mono.empty()
        }
    }
}