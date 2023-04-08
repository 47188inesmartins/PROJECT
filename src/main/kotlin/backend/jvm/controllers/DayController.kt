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
    @ResponseBody
    @PutMapping("/begin/{id}")
    fun updateBeginHour(@PathVariable id:Int,@RequestParam begin: String):Day {
        return dayService.updateBeginHour(id,begin)
    }
    @ResponseBody
    @PutMapping("/end/{id}")
    fun updateEndHour(@PathVariable id:Int,@RequestParam end: String): Day {
        return dayService.updateEndHour(id,end)
    }

}