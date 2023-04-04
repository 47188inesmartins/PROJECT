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

    fun addVacation(vacation: Vacation):Vacation{
        return vacationRepository.save(vacation)
    }

    fun deleteVacation(vacation: Vacation){
        vacationRepository.delete(vacation)
    }

    fun changeBeginDate(id:Int,date: String): Vacation {
        if(!validateDataFormat(date)) throw Exception("Invalid data format")
        return vacationRepository.changeBeginDate(id, Date.valueOf(date))
    }

    fun changeEndDate(id:Int,date: String): Vacation {
        if(!validateDataFormat(date)) throw Exception("Invalid data format")
        return vacationRepository.changeEndDate(id,Date.valueOf(date))
    }

    private fun validateDataFormat(string: String): Boolean {
        val regex = Regex("\\d{4}-\\d{2}-\\d{2}")
        return regex.matches(string)
    }
}