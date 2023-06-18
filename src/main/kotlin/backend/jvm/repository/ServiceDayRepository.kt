package backend.jvm.repository

import backend.jvm.model.ServiceDay
import backend.jvm.model.ServiceDayPK
import org.springframework.data.jpa.repository.JpaRepository

interface ServiceDayRepository: JpaRepository<ServiceDay, ServiceDayPK> {

}