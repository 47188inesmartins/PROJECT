package backend.jvm.services.dto

import java.sql.Time

data class ServiceInputDto(
    val serviceName: String,
    val duration: String,
    val numberMax: Int,
    val price: Double,
    val companyId: Int
)