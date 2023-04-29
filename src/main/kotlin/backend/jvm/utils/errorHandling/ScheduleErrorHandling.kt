package backend.jvm.utils.errorHandling

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Schedule not found")
class ScheduleNotFound: Exception("Schedule not found")

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "The schedule does not has vacation days")
class ScheduleErrorVacation: Exception("The schedule does not has vacation days")
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "The schedule does not have open days")
class ScheduleOpenDays: Exception("The schedule does not have open days")