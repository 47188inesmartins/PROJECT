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
       // if(!validateDataFormat(date)) throw Exception("Invalid data format")
        return vacationRepository.changeBeginDate(id, Date.valueOf(date))
    }

    fun changeEndDate(id:Int,date: String): Vacation {
        //if(!validateDataFormat(date)) throw Exception("Invalid data format")
        return vacationRepository.changeEndDate(id,Date.valueOf(date))
    }

   /*private fun validateDataFormat(string: String): Boolean {
        val regex = """^\d{4}-(0?[1-9]|1[0-2])-([1-9]|[12][0-9]|3[01])$""".toRegex()
        return string.matches(regex)
    }*/
}