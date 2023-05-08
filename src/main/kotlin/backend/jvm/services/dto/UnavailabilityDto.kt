package backend.jvm.services.dto

import backend.jvm.model.UnavailabilityDB
import backend.jvm.model.UserDB
import java.sql.Date
import java.sql.Time

data class UnavailabilityInputDto(
    val dateBegin: String,
    val dateEnd: String?,
    val hourBegin: String?,
    val hourEnd: String?,
    val user: Int
){
    fun mapToUnavailable(dto: UnavailabilityInputDto, userDB: UserDB) :UnavailabilityDB {
        val db = Date.valueOf(dto.dateBegin)
        println(db)
        val de = if(dto.dateEnd != null )Date.valueOf(dto.dateEnd) else null
        println(de)
        val hb = if(dto.hourBegin != null )Time.valueOf(dto.hourBegin) else null
        println(hb)
        val he = if(dto.hourEnd != null )Time.valueOf(dto.hourEnd) else null
        println(he)
        println(dto)
        return UnavailabilityDB(
            dateBegin = db,
            dateEnd = de,
            hourBegin = hb,
            hourEnd =  he,
            userDB = userDB
        )
    }
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
        user = unavailability.userDBId.id
    )
}

