package backend.jvm.controllers

import backend.jvm.controllers.json.ScheduleResponse
import backend.jvm.model.Schedule
import backend.jvm.model.Services
import backend.jvm.services.ScheduleServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/schedule")
class ScheduleController {

    @Autowired
    lateinit var scheduleServices: ScheduleServices

    @ResponseBody
    @PostMapping
    fun addNewSchedule(@RequestBody schedule: Schedule): ResponseEntity<ScheduleResponse> {
        return try{
            val addedSchedule = scheduleServices.addSchedule(schedule)
            val response = ScheduleResponse(addedSchedule.id, addedSchedule.compIdSch.id)
            ResponseEntity.status(201).body(response)
        }catch (e: Exception){
            ResponseEntity.status(400).body(null)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteSchedule(@PathVariable id:Int){
        scheduleServices.deleteSchedule(id)
    }

    @GetMapping("/{id}")
    fun getSchedule(@PathVariable id:Int):Schedule?{
        return scheduleServices.getSchedule(id)
    }

    @ResponseBody
    @GetMapping("/{id}/services/{ids}")
    fun getServices(@PathVariable id: Int,@PathVariable ids: Int): List<Services>{
        return scheduleServices.getServices(id,ids)
    }

    /*@ResponseBody
    @GetMapping("/{id}/client")
    fun getAllClientSchedule(@PathVariable id: Int, @RequestParam date: String, @RequestParam hour: String): List<User>{
        return scheduleServices.getAllClientSchedule(id,date,hour)
    }*/

}