package backend.jvm.services.interfaces

import backend.jvm.model.ServiceDB
import backend.jvm.services.dto.AppointmentInputDto
import backend.jvm.services.dto.AppointmentOutputDto
import backend.jvm.services.dto.ServiceOutputDto
import java.sql.Time

interface IAppointmentServices {

    fun addAppointment(appointment: AppointmentInputDto): AppointmentOutputDto

    fun deleteAppointment(id: Int)

    fun getAppointmentById(id: Int): AppointmentOutputDto?

    fun getAppointmentByDateAndHour (sid: Int, appHour: String, appDate: String) : List<AppointmentOutputDto>

    fun getAvailableServicesByAppointment(beginHour:String, date:String, companyId: Int):List<ServiceOutputDto>

}