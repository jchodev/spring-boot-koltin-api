package com.jerry.spring.demo.security

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
class CustomerAuthenticationEntryPoint : ServerAuthenticationEntryPoint  {

    override fun commence(exchange: ServerWebExchange, ex: AuthenticationException): Mono<Void> {
        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
        exchange.response.headers.contentType = MediaType.APPLICATION_JSON
        val response = exchange.response.bufferFactory().wrap("{\"error\": \"Invalid token or Unauthorized access\"}".toByteArray())
        return exchange.response.writeWith(Mono.just(response))
    }


}