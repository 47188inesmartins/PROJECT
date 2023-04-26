package backend.jvm.utils.errorHandling

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Number max must be greater then 0")
class NumberMaxInvalid:Exception("Number max must be greater then 0")

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Price must be greater then 0")
class PriceInvalid:Exception("Price must be greater then 0")