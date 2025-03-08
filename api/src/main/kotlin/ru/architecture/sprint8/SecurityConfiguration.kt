package ru.architecture.sprint8

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

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
                    //.hasRole("prothetic_user")
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.opaqueToken(Customizer.withDefaults())
            }
            .build()
    }
}