package backend.jvm.controllers

import backend.jvm.model.Schedule
import backend.jvm.model.ServiceDB
import backend.jvm.services.ScheduleServices
import backend.jvm.services.dto.ScheduleInputDto
import backend.jvm.services.dto.ScheduleOutputDto
import backend.jvm.utils.RoleManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/schedule")
class ScheduleController {

    @Autowired
    lateinit var scheduleServices: ScheduleServices

    @RoleManager(["manager"])
    @ResponseBody
    @PostMapping
    fun addNewSchedule(@RequestBody schedule: ScheduleInputDto): ResponseEntity<ScheduleOutputDto> {
        return try{
            val response = scheduleServices.addSchedule(schedule)
            //val response = ScheduleResponse(addedSchedule.id, addedSchedule.company.id)
            ResponseEntity.status(201).body(response)
        }catch (e: Exception){
            ResponseEntity.status(400).body(null)
        }
    }

    @RoleManager(["manager"])
    @DeleteMapping("/{id}")
    fun deleteSchedule(@PathVariable id:Int){
        scheduleServices.deleteSchedule(id)
    }

    @RoleManager(["manager"])
    @GetMapping("/{id}")
    fun getSchedule(@PathVariable id:Int):ScheduleOutputDto?{
          return scheduleServices.getSchedule(id)
    }

    /*@ResponseBody
    @GetMapping("/{id}/services/{ids}")
    fun getServices(@PathVariable id: Int,@PathVariable ids: Int): List<ServiceDB>{
        return scheduleServices.getServices(id,ids)
    }*/

}