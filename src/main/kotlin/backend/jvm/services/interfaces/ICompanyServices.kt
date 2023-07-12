package backend.jvm.services.interfaces

import backend.jvm.model.appointment.AppointmentOutputDto
import backend.jvm.model.company.CompanyEntity
import backend.jvm.model.company.CompanyInputDto
import backend.jvm.model.company.CompanyOutputDto
import backend.jvm.model.day.DayOutputDto
import backend.jvm.model.service.ServiceOutputDto
import backend.jvm.model.vacation.VacationOutputDto

interface ICompanyServices {

    fun getSearchedCompanies(token: String?,search: String?): List<CompanyOutputDto>?

  //  fun addCompany(token: String, company: CompanyInputDto): CompanyOutputDto

    fun deleteCompany(id: Int): Boolean

    fun getCompanyById(id: Int): CompanyOutputDto

    fun getCompanyByNif(nif: String): CompanyOutputDto?

    fun getAllServicesByCompany(id: Int): List<ServiceOutputDto>

    fun getAllAppointmentsByCompany(id: Int):List<AppointmentOutputDto>

    fun getAppointmentByCompanyAndDateAndHour(id: Int, date: String, hour: String): List<AppointmentOutputDto>

    fun getOpenDaysByCompany(id: Int): List<DayOutputDto>

    fun getVacationByCompany(id: Int): List<VacationOutputDto>

    fun changeAddress(id: Int, address: String)

    fun changeDescription(id: Int, description: String): CompanyEntity

    fun getAllCompanies(): List<CompanyOutputDto>

    fun getAllServices(id: Int): List<ServiceOutputDto>

    fun getPersonalizedCompanies(token: String?): List<CompanyOutputDto>?
}