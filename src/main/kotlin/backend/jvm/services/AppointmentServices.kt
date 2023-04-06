package backend.jvm.services

import backend.jvm.model.*
import backend.jvm.repository.AppointmentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
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

    fun addAppointment(appointment: Appointment): Appointment {
        return appointmentRepository.save(appointment)
    }

    fun deleteAppointment(id: Int): Boolean{
        appointmentRepository.deleteById(id)
        return true
    }

    fun getAppointment(id: Int): Optional<Appointment> = appointmentRepository.findById(id)

    fun findAppClientByDateAndHour(cid: Client, date: Date, hour: Time) = appointmentRepository.findAllByAppHourAndCidAndAppDate(hour, cid, date)

    fun findAllByClient(cid: Client) = appointmentRepository.findAllByCid(cid)

    fun findAllBySchedule(sid: Schedule) = appointmentRepository.findAllBySid(sid)

    fun findAllByEmployee(eid: Employee) = appointmentRepository.findAllByEid(eid)

    fun findAllByAppHourAndSidAndAppDate (appHour: Time, sid: Schedule, appDate: Date) : Appointment = appointmentRepository.findAllByAppHourAndSidAndAppDate(appHour,sid,appDate)

    fun getCompany(id: Int) = appointmentRepository.getCompany(id)

    fun editNumberAppPeople(id: Int, numberAppPeople: Int): Appointment = appointmentRepository.editNumberAppPeople(id, numberAppPeople)

    fun editAvailability(id: Int, availability: String): Appointment = appointmentRepository.editAvailability(id, availability)



}