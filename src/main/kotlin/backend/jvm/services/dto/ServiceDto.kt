package backend.jvm.services.dto

import backend.jvm.model.ServiceDB
import java.sql.Time

data class ServiceInputDto(
    val serviceName: String,
    val duration: String,
    val numberMax: Int,
    val price: Double,
    val company: Int
){
    fun mapToService(db: ServiceInputDto, company: Company) : ServiceDB {
        val d = Time.valueOf(duration) ?: throw Exception("invalid time")
        return ServiceDB(
            db.serviceName,
            d,
            db.numberMax,
            db.price,
            company
        )
    }
}

data class ServiceOutputDto(
    val id: Int,
    val serviceName: String,
    val duration: Time,
    val numberMax: Int,
    val price: Double,
    val company: Int
){
    constructor(service : ServiceDB): this(
        id = service.id,
        serviceName = service.name,
        duration = service.duration,
        numberMax = service.numberMax,
        price = service.price,
        company = service.company.id
    )
}