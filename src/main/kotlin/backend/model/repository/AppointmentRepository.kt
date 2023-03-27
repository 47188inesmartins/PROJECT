package backend.model.repository

import backend.domain.Appointment
import org.springframework.data.jpa.repository.JpaRepository

interface AppointmentRepository: JpaRepository<Appointment, Int>{


}