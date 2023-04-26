package backend.jvm.utils.errorHandling


abstract class Errors(val httpStatus : Int, override val message : String) : Exception()

class EmailAlreadyUsed : Errors(409,"Email already in use")

class NifAlreadyExists : Errors( 409,"Nif already exists")

class UserNotExit : Errors(404,"That user doesn't exist")

class ScheduleNotExit : Errors(404,"That schedule doesn't exist")

class CompanyNotExit : Errors(404,"That company doesn't exist")

class ServiceNotExit : Errors(404,"That service doesn't exist")

class UnsafePassword : Errors(406,"Unsafe password")

class Unauthorized : Errors(401,"Unauthorized")

class LoginFailed : Errors(401,"Login failed")
