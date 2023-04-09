package backend.jvm.services

import backend.jvm.model.Vacation
import backend.jvm.repository.VacationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Date

@Service
class VacationService {

    @Autowired
    lateinit var vacationRepository: VacationRepository

    fun getVacation(id: Int): Vacation{
        return vacationRepository.findFirstById(id)
    }

    fun addVacation(vacation: Vacation):Vacation{
        if(!vacation.dateBegin.before(vacation.dateEnd)) throw Exception("Invalid dates")
        return vacationRepository.save(vacation)
    }

    fun deleteVacation(vacation: Vacation){
        vacationRepository.delete(vacation)
    }

    fun changeBeginDate(id:Int,date: String): Vacation {
        val getVacation = vacationRepository.getReferenceById(id)
        val newDate = Date.valueOf(date)?: throw Exception("Invalid new begin date")
        if(!getVacation.dateEnd.after(newDate)) throw Exception("Invalid new begin date")
        return vacationRepository.changeBeginDate(id, newDate)
    }

    fun changeEndDate(id:Int,date: String): Vacation {
        val getVacation = vacationRepository.getReferenceById(id)
        val newDate = Date.valueOf(date) ?: throw Exception("Invalid new end date")
        if(!getVacation.dateBegin.before(newDate)) throw Exception("Invalid new end date")
        return vacationRepository.changeEndDate(id, newDate)
    }
}