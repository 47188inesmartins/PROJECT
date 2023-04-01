package backend.jvm.repository

import backend.jvm.model.Manager
import org.springframework.data.jpa.repository.JpaRepository

interface ManagerRepository : JpaRepository<Manager, Int> {
}