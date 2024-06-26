package com.jerry.spring.demo.security


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SpringSecurityConfig(
    private val securityContextRepository: SecurityContextRepository,
    private val customerAuthenticationEntryPoint: CustomerAuthenticationEntryPoint,
    private val customerServerAccessDeniedHandler: CustomerServerAccessDeniedHandler,
) {

    val PERMITTED_URL = arrayOf(
        "/api/helloeveryone",
        "/api/getToken",

        //swagger
        "/v2/api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",  // -- Swagger UI v3 (OpenAPI)
        "/v3/api-docs/**",
        "/swagger-ui/**", //
    )

    @Bean
    fun springWebFilterChain(serverHttpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
        return serverHttpSecurity
            //.authenticationManager(authenticationManager())
            .securityContextRepository(securityContextRepository)
            .csrf { spec -> spec.disable() }
            .formLogin { spec -> spec.disable() } // Disable form login
            .httpBasic { spec -> spec.disable() } // Disable http basic
            .authorizeExchange { exchanges ->
                exchanges
                    .pathMatchers(*PERMITTED_URL).permitAll()
                    .pathMatchers("/api/hellouser").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                    .pathMatchers("/api/helloadmin").hasAuthority("ROLE_ADMIN")
                    .anyExchange()
                    .authenticated()
            }
            .exceptionHandling {
                it.authenticationEntryPoint(customerAuthenticationEntryPoint)
                it.accessDeniedHandler(customerServerAccessDeniedHandler)
            }
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}