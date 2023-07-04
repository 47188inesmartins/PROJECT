package backend.jvm.services.interfaces

import backend.jvm.model.Company
import backend.jvm.services.dto.*
import java.util.*

interface ICompanyServices {

    fun getSearchedCompanies(search: String?): List<CompanyOutputDto>?

    fun addCompany(token: String, company: CompanyInputDto): CompanyOutputDto

    fun deleteCompany(id: Int): Boolean

    fun getCompanyById(id: Int): CompanyOutputDto

    fun getCompanyByNif(nif: String): CompanyOutputDto?

    fun getAllServicesByCompany(id: Int): List<ServiceOutputDto>

    fun getAllAppointmentsByCompany(id: Int):List<AppointmentOutputDto>

    fun getAppointmentByCompanyAndDateAndHour(id: Int, date: String, hour: String): List<AppointmentOutputDto>

    fun getOpenDaysByCompany(id: Int): List<DayOutputDto>

    fun getVacationByCompany(id: Int): List<VacationOutputDto>

    fun changeAddress(id: Int, address: String)

    fun changeDescription(id: Int, description: String): Company

    fun getAllCompanies(): List<CompanyOutputDto>

    fun getAllServices(id: Int): List<ServiceOutputDto>
}