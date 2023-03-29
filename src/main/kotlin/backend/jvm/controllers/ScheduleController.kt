package backend.jvm.controllers

import backend.jvm.services.CompanyServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController


@RestController
class ScheduleController {
    @Autowired
    lateinit var companyServices: CompanyServices
}