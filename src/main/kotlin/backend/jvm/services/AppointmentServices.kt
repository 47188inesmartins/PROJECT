package backend.jvm.services

import backend.jvm.model.*
import backend.jvm.repository.AppointmentRepository
import backend.jvm.repository.ServiceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Time
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


    fun addAppointment(appointment: Appointment): Int {
        val existingAppointment = appointmentRepository.findByAppDateAndAppHourAndSid(appointment.appDate!!, appointment.appHour, appointment.sid!!.id!!)
        if (existingAppointment != null) {
            appointment.numberAppPeople = existingAppointment.numberAppPeople!!.plus(1)
        }
        val savedAppointment = appointmentRepository.save(appointment)
      /*  if (savedAppointment.number_app_people == max_number) {
            appointmentRepository.updateAvailability(savedAppointment.id!!, "unavailable")
        }*/
        return savedAppointment.id!!
    }

    fun deleteAppointment(id: Int): Boolean{
        appointmentRepository.deleteById(id)
        return true
    }

    fun getAppointment(id: Int): Optional<Appointment> = appointmentRepository.findById(id)


    /*fun findAppClientByDateAndHour(cid: Client, date: Date, hour: Time) = appointmentRepository.findAllByAppHourAndCidAndAppDate(hour, cid, date)

    fun findAllByClient(cid: Client) = appointmentRepository.findAllByCid(cid)

    fun findAllBySchedule(sid: Schedule) = appointmentRepository.findAllBySid(sid)

    fun findAllByEmployee(eid: Employee) = appointmentRepository.findAllByEid(eid)*/

    fun findAllByAppHourAndSidAndAppDate (appHour: Time, sid: Schedule, appDate: Date) : Appointment = appointmentRepository.findAllByAppHourAndSidAndAppDate(appHour,sid,appDate)

  //  fun getCompany(id: Int) = appointmentRepository.getCompany(id)

    fun editNumberAppPeople(id: Int, numberAppPeople: Int): Appointment = appointmentRepository.editNumberAppPeople(id, numberAppPeople)

    fun editAvailability(id: Int, availability: String): Appointment = appointmentRepository.editAvailability(id, availability)


}