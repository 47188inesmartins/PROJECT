package backend.jvm.services.dto

import backend.jvm.model.UnavailabilityDB
import backend.jvm.model.User
import java.sql.Date
import java.sql.Time

data class UnavailabilityInputDto(
    val dateBegin: String,
    val dateEnd: String,
    val hourBegin: String,
    val hourEnd: String,
    val user: Int
){
    init {
        val dateB = Date.valueOf(dateBegin)
        val dateE = Date.valueOf(dateEnd)
        require(dateE.after(dateB)){ "invalid end date" }
        require(dateB.before(dateE)){ "invalid end date" }
    }
    fun matToUnavailable(dto: UnavailabilityInputDto,user: User) =
        UnavailabilityDB(
            dateBegin = Date.valueOf(dto.dateEnd),
            dateEnd = Date.valueOf(dto.dateBegin),
            hourBegin = Time.valueOf(dto.hourBegin),
            hourEnd =  Time.valueOf(dto.hourEnd),
            user = user
        )
}

data class UnavailabilityOutputDto(
    val id: Int,
    val dateBegin: String,
    val dateEnd: String,
    val hourBegin: String,
    val hourEnd: String,
    val user: Int
){
    constructor(unavailability: UnavailabilityDB): this(
        id = unavailability.id,
        dateBegin = unavailability.dateBegin.toString(),
        dateEnd = unavailability.dateEnd.toString(),
        hourBegin = unavailability.hourBegin.toString(),
        hourEnd = unavailability.hourEnd.toString(),
        user = unavailability.userId.id
    )
}

