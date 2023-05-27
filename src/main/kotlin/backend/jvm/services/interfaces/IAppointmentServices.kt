package backend.jvm.services.interfaces

import backend.jvm.model.ServiceDB
import backend.jvm.services.dto.AppointmentInputDto
import backend.jvm.services.dto.AppointmentOutputDto
import backend.jvm.services.dto.ServiceOutputDto
import java.sql.Time

interface IAppointmentServices {
    fun addAppointment(appointment: AppointmentInputDto): AppointmentOutputDto
    fun deleteAppointment(id: Int)
    fun getAppointment(id: Int): AppointmentOutputDto?
    fun getAppointmentByDateAndHour (sid: Int, appHour: String, appDate: String) : List<AppointmentOutputDto>
    fun getAvailableServicesByEmployees(beginHour:String, date:String, companyId: Int):List<ServiceOutputDto>
    fun availableServicesByDay(companyId: Int, day: String,  beginHour: Time): List<ServiceDB>
    fun getAllAppointmentsByUser(user: Int): List<AppointmentOutputDto>
    fun getAvailableServices(beginHour:String, date:String, companyId: Int):List<ServiceOutputDto>
}