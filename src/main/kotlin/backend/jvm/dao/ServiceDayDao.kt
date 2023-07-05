package backend.jvm.dao

import backend.jvm.model.ServiceDay
import backend.jvm.model.ServiceDayPK
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying

interface ServiceDayDao: JpaRepository<ServiceDay, ServiceDayPK> {

    @Modifying
    fun deleteAllByService_Id(service_id: Int)
}