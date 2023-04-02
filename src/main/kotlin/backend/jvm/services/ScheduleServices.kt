package backend.jvm.services

import backend.jvm.model.Schedule
import backend.jvm.model.Vacation
import backend.jvm.repository.ScheduleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class ScheduleServices {

    @Autowired
    lateinit var scheduleRepository: ScheduleRepository





}