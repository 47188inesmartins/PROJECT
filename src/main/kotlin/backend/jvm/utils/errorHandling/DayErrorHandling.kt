package backend.jvm.utils.errorHandling

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid day of week")
class InvalidOpenDay : Exception("Invalid day of week")

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Schedule not found")
class InvalidSchedule : Exception(" Schedule not found ")

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Day not found")
class InvalidDay : Exception("Day not  found")

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No available days")
class NoAvailableDays : Exception("No available days")

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No available services")
class NoAvailableServices(day: String) : Exception("No available services at: $day")
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Date is no available for appointments")
class InvalidDate: Exception("Date is no available for appointments")