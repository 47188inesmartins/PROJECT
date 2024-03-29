package backend.jvm.services.interfaces

import backend.jvm.model.vacation.VacationInputDto
import backend.jvm.model.vacation.VacationOutputDto
import java.sql.Date

interface IVacationServices {
    fun getVacation(id: Int): VacationOutputDto

    fun addVacation(vacation: VacationInputDto, company: Int): VacationOutputDto

    fun deleteVacation(vacation: Int)

    fun changeBeginDate(id:Int, date: String): Date

    fun changeEndDate(id:Int,date: String): Date

}