package backend.jvm.services.interfaces

import backend.jvm.model.appointment.AppointmentInputDto
import backend.jvm.model.appointment.AppointmentOutputDto
import backend.jvm.model.service.ServiceOutputDto
import backend.jvm.model.user.UserOutputDto

interface IAppointmentServices {

    fun addAppointment(appointment: AppointmentInputDto, cid: Int, token: String): AppointmentOutputDto

    fun deleteAppointment(id: Int)

    fun getAppointmentById(id: Int): AppointmentOutputDto?

    fun getAppointmentByDateAndHour (sid: Int, appHour: String, appDate: String) : List<AppointmentOutputDto>

    fun getAvailableServicesByAppointment(beginHour:String, date:String, companyId: Int): List<Pair<ServiceOutputDto, List<UserOutputDto>>>

}