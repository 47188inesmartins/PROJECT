package backend.jvm.services.interfaces

import backend.jvm.model.Company
import backend.jvm.services.dto.*
import java.util.*

interface ICompanyServices {
    fun addCompany(token: String, company: CompanyInputDto): CompanyOutputDto

    fun deleteCompany(id: Int): Boolean

    fun getCompany(id: Int): CompanyOutputDto

    fun findCompanyByNif(nif: String): Company?

    fun getAllServices(id: Int): List<ServiceOutputDto>

    fun getAllAppointments(id: Int):List<AppointmentOutputDto>

    fun getAppointment(id: Int, date: String, hour: String): List<AppointmentOutputDto>

    fun getOpenDays(id: Int): List<DayOutputDto>

    fun getVacation(id: Int): List<VacationOutputDto>

    fun changeAddress(id: Int, address: String)

    fun changeDescription(id: Int, description: String): Company
}