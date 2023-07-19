package backend.jvm.services

import backend.jvm.dao.ScheduleDao
import backend.jvm.dao.VacationDao
import backend.jvm.model.vacation.VacationInputDto
import backend.jvm.model.vacation.VacationOutputDto
import backend.jvm.services.interfaces.IVacationServices
import backend.jvm.utils.errorHandling.CompanyNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Date

@Service
class VacationService : IVacationServices {

    @Autowired
    lateinit var vacationDao: VacationDao

    @Autowired
    lateinit var scheduleDao: ScheduleDao

    override fun addVacation(vacation: VacationInputDto, company: Int): VacationOutputDto {
        val schedule = scheduleDao.getScheduleByCompany_Id(company)?: throw CompanyNotFound()
        val db = vacation.mapToVacationDb(vacation,schedule)
        if(!db.date_begin.before(db.date_end)) throw Exception("Invalid dates")
        val vt = vacationDao.save(db)
        return VacationOutputDto(vt)
    }

    override fun getVacation(id: Int): VacationOutputDto {
        val vacation = vacationDao.findById(id)
        if(vacation.isEmpty) throw Exception("The company doesn't has vacation")
        return VacationOutputDto(vacation.get())
    }

    override fun deleteVacation(vacation: Int){
        vacationDao.deleteById(vacation)
    }

    override fun changeBeginDate(id:Int, date: String): Date {
        val getVacation = vacationDao.getReferenceById(id)
        val newDate = Date.valueOf(date)?: throw Exception("Invalid new begin date")
        if(!getVacation.date_begin.after(newDate)) throw Exception("Invalid new begin date")
        return vacationDao.changeBeginDate(id, newDate)
    }

    override fun changeEndDate(id:Int,date: String): Date {
        val getVacation = vacationDao.getReferenceById(id)
        val newDate = Date.valueOf(date) ?: throw Exception("Invalid new end date")
        if(!getVacation.date_begin.before(newDate)) throw Exception("Invalid new end date")
        return vacationDao.changeEndDate(id, newDate)
    }

}