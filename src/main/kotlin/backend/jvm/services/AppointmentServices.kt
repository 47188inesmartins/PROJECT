package backend.jvm.services

import backend.jvm.model.*
import backend.jvm.repository.AppointmentRepository
import backend.jvm.repository.ServiceRepository
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
    lateinit var servicesRepository: ServiceRepository

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


    fun changeAvailabilityByPeople(appointment: Appointment,services: Services):Appointment{
        val getAppointment = appointment.id?.let { appointmentRepository.getReferenceById(it) }
            ?: throw Exception("Appointment does not exists")

        val getService = services.id?.let { servicesRepository.getServicesById(it) }
            ?: throw Exception("Service does not exists")

       /* if(getAppointment.numberAppPeople == getService.numberMax){
            return appointmentRepository.editAvailability(appointment.id!!,"unavailable")
        }*/
        return appointment
    }

    fun addAppointment(appointment: Appointment): Appointment {
      //  val existingAppointment = appointmentRepository.findAllByAppDateAndAppHourAndSid(appointment.appDate!!, appointment.appHour, appointment.sid!!)?.lastOrNull()
      /*  if (existingAppointment != null) {
            println(existingAppointment.numberAppPeople)
            appointment.numberAppPeople = existingAppointment.numberAppPeople?.plus(1)
        }
        println("id=" + appointment.id)*/
        val savedAppointment = appointmentRepository.save(appointment)
        println("save = ${savedAppointment.id}")
        /*if (savedAppointment.number_app_people == max_number) {
            appointmentRepository.updateAvailability(savedAppointment.id!!, "unavailable")
        }*/
        return savedAppointment
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


}