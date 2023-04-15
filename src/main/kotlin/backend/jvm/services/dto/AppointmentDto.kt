package backend.jvm.services.dto

import java.sql.Date
import java.sql.Time


//data class AppointmentResponse(val id: Int, val appHour: Time, val appDate: Date, val sid: Int, val uid: Int?)


data class AppointmentInputDto(val appHour: String, val appDate: String, val scheduleId: Int, val userId: Int?, val service: Int)

data class AppointmentOutputDto(val id: Int, val appHour: Time, val appDate: Date, val scheduleId: Int, val userId: Int?, val service: Int)


//class AppointmentInputDto
/*    val appHour: Time,
    val appDate: Date,
    val sid: Int,
    val uid: Int,
    val services: List<Services>*/
  //  {
 /*   val appHour: Time
    val appDate: Date
    val sid: Int
    val uid: Int
    val services: List<Int>
u
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


}*/