package backend.jvm.controllers

import backend.jvm.model.Client
import backend.jvm.model.Schedule
import backend.jvm.model.Services
import backend.jvm.services.CompanyServices
import backend.jvm.services.ScheduleServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/schedule")
class ScheduleController {
    @Autowired
    lateinit var scheduleServices: ScheduleServices

    @ResponseBody
    @PostMapping
    fun addNewSchedule(@RequestBody schedule: Schedule):Schedule{
        return scheduleServices.addSchedule(schedule)
    }
    @DeleteMapping("/{id}")
    fun deleteSchedule(@PathVariable id:Int){
        scheduleServices.deleteSchedule(id)
    }

    @GetMapping("/{id}")
    fun getSchedule(@PathVariable id:Int){
        scheduleServices.getSchedule(id)
    }
    @ResponseBody
    @GetMapping("/{id}/services/{ids}")
    fun getServices(@PathVariable id: Int,@PathVariable ids: Int): List<Services>{
        return scheduleServices.getServices(id,ids)
    }
    @GetMapping("/{id}/client")
    fun getAllClientSchedule(@PathVariable id: Int, @RequestParam date: String,@RequestParam hour: String): List<Client>{
        return scheduleServices.getAllClientSchedule(id,date,hour)
    }
}