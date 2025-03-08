package ru.architecture.sprint8

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfiguration {
    @Bean
    fun springSecurityFilterChain(
        serverHttpSecurity: ServerHttpSecurity
    ): SecurityWebFilterChain {
        return serverHttpSecurity
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .authorizeExchange { exchanges: ServerHttpSecurity.AuthorizeExchangeSpec ->
                exchanges
                    .pathMatchers("/reports")
                    .authenticated()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.opaqueToken(Customizer.withDefaults())
            }
            .build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowCredentials = true
            allowedHeaders = listOf(CorsConfiguration.ALL)
            allowedMethods = listOf(CorsConfiguration.ALL)
            allowedOrigins = listOf("http://localhost:3000")
        }
        val source = UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
        return source
    }

}