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

    fun changeBeginDate(id:Int,date: Date): Vacation {
        return vacationRepository.changeBeginDate(id,date)
    }

    fun changeEndDate(id:Int,date: Date): Vacation {
        return vacationRepository.changeEndDate(id,date)
    }
}