package backend.jvm.services

import backend.jvm.repository.ScheduleRepository
import backend.jvm.repository.VacationRepository
import backend.jvm.services.dto.VacationInputDto
import backend.jvm.services.dto.VacationOutputDto
import backend.jvm.services.interfaces.IVacationServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Date

@Service
class VacationService : IVacationServices {

    @Autowired
    lateinit var vacationRepository: VacationRepository

    @Autowired
    lateinit var scheduleRepository: ScheduleRepository

    override fun getVacation(id: Int): VacationOutputDto {
        val vacation = vacationRepository.findById(id)
        if(vacation.isEmpty) throw Exception("The company doesn't has vacation")
        return VacationOutputDto(vacation.get())
    }

    override fun addVacation(vacation: VacationInputDto):VacationOutputDto {
        val schedule = scheduleRepository.getReferenceById(vacation.schedule)
        val db = vacation.mapToVacationDb(vacation,schedule)
        if(!db.dateBegin.before(db.dateEnd)) throw Exception("Invalid dates")
        return VacationOutputDto(vacationRepository.save(db))
    }

    override fun deleteVacation(vacation: Int){
        vacationRepository.deleteById(vacation)
    }

    override fun changeBeginDate(id:Int, date: String): Date {
        val getVacation = vacationRepository.getReferenceById(id)
        val newDate = Date.valueOf(date)?: throw Exception("Invalid new begin date")
        if(!getVacation.dateBegin.after(newDate)) throw Exception("Invalid new begin date")
        return vacationRepository.changeBeginDate(id, newDate)
    }

    override fun changeEndDate(id:Int,date: String): Date {
        val getVacation = vacationRepository.getReferenceById(id)
        val newDate = Date.valueOf(date) ?: throw Exception("Invalid new end date")
        if(!getVacation.dateBegin.before(newDate)) throw Exception("Invalid new end date")
        return vacationRepository.changeEndDate(id, newDate)
    }
}