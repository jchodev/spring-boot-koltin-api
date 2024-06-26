package com.jerry.spring.demo.security

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class CustomerServerAccessDeniedHandler: ServerAccessDeniedHandler {

    override fun handle(exchange: ServerWebExchange, denied: AccessDeniedException): Mono<Void> {

        exchange.response.statusCode = HttpStatus.FORBIDDEN
        exchange.response.headers.contentType = MediaType.APPLICATION_JSON
        val response = exchange.response.bufferFactory().wrap("{\"error\": \"CustomerServerAccessDeniedHandler\"}".toByteArray())
        return exchange.response.writeWith(Mono.just(response))

    }
}