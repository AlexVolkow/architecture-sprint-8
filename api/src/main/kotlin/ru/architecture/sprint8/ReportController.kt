package ru.architecture.sprint8

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.random.Random

@RestController
class ReportController {

    @GetMapping("/reports")
    fun generateReport(): Report {
        return Report(
            reportName = "Тестовый отчет",
            data = List(Random.nextInt(10)) {
                Report.Metric(
                    name = "Метрика-${Random.nextInt(100)}",
                    value = Random.nextInt(),
                    userId = UUID.randomUUID()
                )
            }
        )
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