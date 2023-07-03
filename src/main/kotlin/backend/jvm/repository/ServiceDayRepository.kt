package backend.jvm.repository

import backend.jvm.model.Schedule
import backend.jvm.model.ServiceDay
import backend.jvm.model.ServiceDayPK
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ServiceDayRepository: JpaRepository<ServiceDay, ServiceDayPK> {

    @Modifying
    fun deleteAllByService_Id(service_id: Int)
}