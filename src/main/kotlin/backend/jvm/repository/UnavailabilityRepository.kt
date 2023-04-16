package backend.jvm.repository

import backend.jvm.model.UnavailabilityDB
import backend.jvm.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.sql.Date

interface UnavailabilityRepository:JpaRepository<UnavailabilityDB,Int> {

    fun getUnavailabilityDBByUserId(userId: User): UnavailabilityDB

    @Query(value = "update unavailability set date_end =:date where id = :id returning date_end", nativeQuery = true)
    fun updateDateEnd(@Param("date")date:Date, @Param("id")id: Int): Date

}