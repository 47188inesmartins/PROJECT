package backend.jvm.repository

import backend.jvm.model.Vacation
import org.springframework.data.jpa.repository.JpaRepository

interface VacationRepository: JpaRepository<Vacation, Int>{

}