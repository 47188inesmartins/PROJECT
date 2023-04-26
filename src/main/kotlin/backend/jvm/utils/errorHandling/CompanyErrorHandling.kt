package backend.jvm.utils.errorHandling

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Company Not Found")
class CompanyNotFound:Exception("Company Not Found ")

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Nif already exists")
class NifAlreadyExist:Exception("Nif already exists")

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Nif is invalid")
class InvalidNif:Exception("Nif is invalid")