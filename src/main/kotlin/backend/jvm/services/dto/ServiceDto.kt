package backend.jvm.services.dto

import backend.jvm.model.ServiceDB
import java.security.Provider.Service
import java.sql.Time

data class ServiceInputDto(
    val serviceName: String,
    val duration: String,
    val numberMax: Int,
    val price: Double,
    val company: Int
)

data class ServiceOutputDto(
    val serviceName: String,
    val duration: Time,
    val numberMax: Int,
    val price: Double,
    val company: Int
){
    constructor(service : ServiceDB): this(
        serviceName = service.name,
        duration = service.duration,
        numberMax = service.numberMax,
        price = service.price,
        company = service.company.id
    )
}