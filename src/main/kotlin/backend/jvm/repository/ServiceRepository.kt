package backend.jvm.repository

import backend.jvm.model.Service
import org.springframework.data.jpa.repository.JpaRepository

interface ServiceRepository : JpaRepository<Service, Int> {
}