package backend.jvm.services.dto

import backend.jvm.model.Appointment
import backend.jvm.model.Services
import java.sql.Date
import java.sql.Time
import java.util.ListResourceBundle


//data class AppointmentResponse(val id: Int, val appHour: Time, val appDate: Date, val sid: Int, val uid: Int?)

class AppointmentInputDto
/*    val appHour: Time,
    val appDate: Date,
    val sid: Int,
    val uid: Int,
    val services: List<Services>*/
    {
    val appHour: Time
    val appDate: Date
    val sid: Int
    val uid: Int
    val services: List<Int>

    constructor(){
        this.appHour = Time.valueOf("00:00:00")
        this.appDate = Date.valueOf("2000-01-01")
        this.sid = 0
        this.uid = 0
        this.services = listOf()
    }


    constructor(appHour: String, appDate: String, sid: Int, uid: Int, services: List<Int>){
        this.appHour = Time.valueOf(appHour)
        this.appDate = Date.valueOf(appDate)
        this.sid = sid
        this.uid = uid
        this.services = services
    }


}