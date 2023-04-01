package backend.jvm.repository

//import backend.jvm.model.Day
import backend.jvm.model.Day
import org.springframework.data.jpa.repository.JpaRepository

interface DayRepository : JpaRepository<Day, Int> {
}