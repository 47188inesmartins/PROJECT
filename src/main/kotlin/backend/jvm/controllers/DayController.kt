package backend.jvm.controllers

import backend.jvm.model.Day
import backend.jvm.services.DayServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/day")
class DayController {

    @Autowired
    lateinit var dayService:DayServices

    @ResponseBody
    @PostMapping
    fun addOpenDay(@RequestBody day: Day): Day {
        return dayService.addOpenDay(day)
    }

    @DeleteMapping
    fun deleteOpenDay(@RequestBody day: Day) {
        dayService.deleteDay(day)
    }

    @PutMapping("/begin/{id}")
    fun updateBeginHour(@PathVariable id:Int,@RequestParam begin: String) {
        dayService.updateBeginHour(id,begin)
    }

    @PutMapping("/end/{id}")
    fun updateEndHour(@PathVariable id:Int,@RequestParam end: String) {
        dayService.updateBeginHour(id,end)
    }

}