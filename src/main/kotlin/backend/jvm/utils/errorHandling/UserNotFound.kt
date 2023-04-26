package backend.jvm.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User Not Found")
class UserNotFound: Exception(" User Not Found ")

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Insecure Password")
class InvalidPassword: Exception("Insecure Password")

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Credentials")
class InvalidCredentials(message: String): Exception(message)

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Token")
class InvalidToken: Exception("Invalid Token")

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Email already exists")
class EmailAlreadyExists: Exception("Email already exists")