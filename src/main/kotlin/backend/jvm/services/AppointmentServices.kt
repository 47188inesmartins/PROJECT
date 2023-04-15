package backend.jvm.services

import backend.jvm.model.*
import backend.jvm.repository.AppointmentRepository
import backend.jvm.services.dto.AppointmentInputDto
import backend.jvm.services.dto.AppointmentOutputDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Time
import java.sql.Date
import java.util.*


/**
 *
 *
 * --mafalda
## Appointment

>#### POST
> add an appointment [/appointment] @Params in body

>#### GET
> get specific appointment [/appointment/{id}] @RequestParams data and hour
>
> get specific appointment by date and hour [/appointment]
>
> get client info [/appointment/{id}/client]

>#### DELETE
> delete an appointment [/appointment/{id}]

>#### PUT
> change number of people scheduled for that appointment [/appointment/{id}/scheduled] @RequestBody new number
>
> change availability [/appointment/{id}/availability]

 *
 */


@Service
class AppointmentServices {

    @Autowired
    lateinit var appointmentRepository: AppointmentRepository

    @Autowired
    lateinit var servicesService: ServServices

    @Autowired
    lateinit var userService: UserServices

    @Autowired
    lateinit var scheduleService: ScheduleServices


   /* fun addAppointment(appointment: Appointment): Int {
        val id = appointmentRepository.save(appointment).id
        id?.let {
            appointmentRepository.increaseAppointmentNumber(it)
            val updatedAppointment = appointmentRepository.findById(it).orElse(null)
           /* if (updatedAppointment != null && updatedAppointment.numberAppPeople >= max_number) {
                appointmentRepository.editAvailability(it, "unavailable")
                updatedAppointment.availability = "unavailable"
            }*/
        }
        return id
    }*/

    fun toAppointment(dto: AppointmentInputDto): Appointment {
     /*   val appointment = Appointment(
            dto.appHour,
            dto.appDate,
            dto.sid,
            dto.uid,
            dto.services


        )*/
        TODO()
    }

 /*   fun changeAvailabilityByPeople(appointment: Appointment,services: Services):Appointment{
        val getAppointment = appointment.id?.let { appointmentRepository.getReferenceById(it) }
            ?: throw Exception("Appointment does not exists")

        val getService = services.id?.let { servicesRepository.getServicesById(it) }
            ?: throw Exception("Service does not exists")

       /* if(getAppointment.numberAppPeople == getService.numberMax){
            return appointmentRepository.editAvailability(appointment.id!!,"unavailable")
        }*/
        return appointment
    }*/

    fun addAppointment(appointment: AppointmentInputDto): AppointmentOutputDto {
        val service = servicesService.getServiceById(appointment.service)
        val user = if (appointment.userId != null) userService.getUserById(appointment.userId).get() else null
        val schedule = scheduleService.getSchedule(appointment.scheduleId)!!
        val app = Appointment(
            appHour = Time.valueOf(appointment.appHour),
            appDate = Date.valueOf(appointment.appDate),
            scheduleId = schedule,
            userId = user,
            serviceDB = service
        )

        val savedAppointment = appointmentRepository.save(app)

        return AppointmentOutputDto(
            savedAppointment.id,
            savedAppointment.appHour,
            savedAppointment.appDate,
            savedAppointment.scheduleId.id,
            savedAppointment.userId?.id,
            savedAppointment.service.id
        )
    }

    fun getServices(id: Int): List<Int> {
        return appointmentRepository.getServices(id)
    }

    fun deleteAppointment(id: Int){
        appointmentRepository.deleteById(id)
    }

    fun increaseNumberOfPeopleApp(id:Int, maxNumber:Int){
        require(id>0 && maxNumber>0)
        appointmentRepository.increaseAppointmentNumber(id,maxNumber)
    }

    fun getAppointment(id: Int): Optional<Appointment> = appointmentRepository.findById(id)


    /*fun findAppClientByDateAndHour(cid: Client, date: Date, hour: Time) = appointmentRepository.findAllByAppHourAndCidAndAppDate(hour, cid, date)

    fun findAllByClient(cid: Client) = appointmentRepository.findAllByCid(cid)

    fun findAllBySchedule(sid: Schedule) = appointmentRepository.findAllBySid(sid)

    fun findAllByEmployee(eid: Employee) = appointmentRepository.findAllByEid(eid)*/

    fun getAppointmentByDateAndHour (appHour: Time, sid: Int, appDate: Date) : List<Appointment> {
        println("date services = " + appDate)
        println("hour services = " + appHour)
        return appointmentRepository.getAppointmentByDateAndHour(sid, appDate, appHour)
    }
  //  fun getCompany(id: Int) = appointmentRepository.getCompany(id)

    fun editNumberAppPeople(id: Int, numberAppPeople: Int): Appointment = appointmentRepository.editNumberAppPeople(id, numberAppPeople)

 //   fun editAvailability(id: Int, availability: String): Appointment = appointmentRepository.editAvailability(id, availability)



    fun mapToResponse(appointmentBD : Appointment): AppointmentOutputDto{
        TODO()
    }


}