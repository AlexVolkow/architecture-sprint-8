package ru.architecture.sprint8

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.*
import kotlin.random.Random


@RestController
class ReportController {

    @GetMapping("/reports")
    fun generateReport(): Mono<ResponseEntity<Report>> {
        return ReactiveSecurityContextHolder.getContext().map {
            val roles = getRealmRoles(it)

            if ("prothetic_user" in roles) {
                val report = Report(
                    reportName = "Тестовый отчет",
                    data = List(Random.nextInt(10)) {
                        Report.Metric(
                            name = "Метрика-${Random.nextInt(100)}",
                            value = Random.nextInt(),
                            userId = UUID.randomUUID()
                        )
                    }
                )

                ResponseEntity
                    .status(HttpStatus.OK)
                    .body(report)
            } else {
                ResponseEntity.status(HttpStatus.FORBIDDEN).build()
            }
        }
    }

    private fun getRealmRoles(securityContext: SecurityContext): List<String> {
        val principal = securityContext.authentication.principal as OAuth2AuthenticatedPrincipal
        val realm = principal.attributes["realm_access"]
        val realRoles = realm as? Map<String, List<String>>
        return realRoles?.get("roles").orEmpty()
    }
}

data class Report(
    val reportName: String,
    val data: List<Metric>
) {
    data class Metric(
        val name: String,
        val value: Int,
        val userId: UUID
    )
}