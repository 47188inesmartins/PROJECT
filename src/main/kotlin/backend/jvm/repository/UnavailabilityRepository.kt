package backend.jvm.repository

import backend.jvm.model.UnavailabilityDB
import backend.jvm.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UnavailabilityRepository:JpaRepository<UnavailabilityDB,Int> {

    fun getUnavailabilityDBByUserId(userId: User): UnavailabilityDB

}