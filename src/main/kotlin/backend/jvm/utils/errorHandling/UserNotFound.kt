package backend.jvm.utils.errorHandling

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User Not Found")
class UserNotFound: Exception(" User Not Found ")

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Insecure Password")
class InvalidPassword: Exception("Insecure Password")

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Email")
class InvalidEmail: Exception("Invalid email")

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Token")
class InvalidToken: Exception("Invalid Token")

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Email already exists")
class EmailAlreadyExists: Exception("Email already exists")

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Password Insecure")
class PasswordInsecure: Exception("Password Insecure")

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid credentials")
class InvalidCredentials: Exception("Invalid credentials")

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid User")
class InvalidUser: Exception("Invalid User")

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Invalid User")
class AlreadyEmployee: Exception("Already an employee")