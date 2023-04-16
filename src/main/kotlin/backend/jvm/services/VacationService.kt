package backend.jvm.services

import backend.jvm.model.Vacation
import backend.jvm.repository.ScheduleRepository
import backend.jvm.repository.VacationRepository
import backend.jvm.services.dto.VacationInputDto
import backend.jvm.services.dto.VacationOutputDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Date

@Service
class VacationService {

    @Autowired
    lateinit var vacationRepository: VacationRepository

    @Autowired
    lateinit var scheduleRepository: ScheduleRepository

    fun getVacation(id: Int): VacationOutputDto {
        val vacation = vacationRepository.findById(id)
        if(vacation.isEmpty) throw Exception("The company doesn´t has vacation")
        return VacationOutputDto(vacation.get())
    }

    fun addVacation(vacation: VacationInputDto):VacationOutputDto {
        val schedule = scheduleRepository.findById(vacation.schedule).get()
        val db = vacation.mapToVacationDb(vacation,schedule)
        if(!db.dateBegin.before(db.dateEnd)) throw Exception("Invalid dates")
        return VacationOutputDto(vacationRepository.save(db))
    }

    fun deleteVacation(vacation: Int){
        vacationRepository.deleteById(vacation)
    }

    fun changeBeginDate(id:Int, date: String): Date {
        val getVacation = vacationRepository.getReferenceById(id)
        val newDate = Date.valueOf(date)?: throw Exception("Invalid new begin date")
        if(!getVacation.dateBegin.after(newDate)) throw Exception("Invalid new begin date")
        return vacationRepository.changeBeginDate(id, newDate)
    }

    fun changeEndDate(id:Int,date: String): Date {
        val getVacation = vacationRepository.getReferenceById(id)
        val newDate = Date.valueOf(date) ?: throw Exception("Invalid new end date")
        if(!getVacation.dateBegin.before(newDate)) throw Exception("Invalid new end date")
        return vacationRepository.changeEndDate(id, newDate)
    }
}