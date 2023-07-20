package backend.jvm.services

import backend.jvm.dao.*
import backend.jvm.model.*
import backend.jvm.model.appointment.AppointmentEntity
import backend.jvm.model.appointment.AppointmentInfo
import backend.jvm.model.appointment.AppointmentsUserInfo
import backend.jvm.model.user.*
import backend.jvm.services.interfaces.IUserInterface
import backend.jvm.utils.*
import backend.jvm.utils.errorHandling.*
import backend.jvm.utils.hashUtils.PasswordDecoder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.sql.Date
import java.util.*


@Service
class UserServices : IUserInterface {

    @Autowired
    lateinit var userDao: UserDao
    @Autowired
    lateinit var servicesRepository: ServiceDao
    @Autowired
    lateinit var appointmentDao: AppointmentDao
    @Autowired
    lateinit var companyDao: CompanyDao
   /* @Autowired
    lateinit var roleDao: RoleDao*/
    @Autowired
    lateinit var userCompanyDao: UserCompanyDao
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var passwordDecoder: PasswordDecoder

    companion object{
        val EMAIL_FORMAT = Regex("""^\w+@\w+\.\w+$""")
        val PASSWORD_FORMAT = Regex("^(?=.*\\d)(?=.*[!@#\$%^&*])(?=.*[a-zA-Z]).{8,}$")
    }

    override fun addUser(user: UserInputDto): CreatedUserOutput {
        if(!EMAIL_FORMAT.matches(user.email)) throw InvalidEmail()
        if(!PASSWORD_FORMAT.matches(user.password)) throw PasswordInsecure()
        if(userDao.getUsersByEmail(user.email) != null) throw EmailAlreadyExists()

        val coordinates = getUserCoordinates(user.street,user.city,user.country)
        val encodePassword = passwordEncoder.encode(user.password)


        val returnUser = userDao.save(
            user.mapToUser(
                user,
                encodePassword,
                user.interests,
                null,
                coordinates)
        )
        return CreatedUserOutput(returnUser.id, returnUser.token)
    }



    override fun deleteUser(id: Int): Boolean {
        if(userDao.findById(id).isEmpty) return false
        userDao.deleteById(id)
        return true
    }

    override fun getUserById(token: String): UserOutputDto {
        val getUser = userDao.getUserByToken(UUID.fromString(token)) ?: throw UserNotFound()
        return UserOutputDto(getUser)
    }

    override fun changeRole(id: Int, name: String): String {
        return userDao.changeRole(id,name)
    }

    override fun changePassword(password: String, id: Int): String{
        if(!Hashing.verifyPasswordSecure(password)) throw InvalidPassword()
        val pass = Hashing.encodePass(password)
        return userDao.changePassword(pass, id)
    }

    override fun getRoleByUserAndCompany (compId: Int, userId: Int): String? {
        return userCompanyDao.getRoleByCompanyAndUser(compId,userId)
    }

    fun getUsersByEmailAndPassword (email: String, password: String): Pair<String, List<CompanyRole>> {
        val user = userDao.getUsersByEmail(email) ?: throw UserNotFound()
        val token = user.token.toString()
        val passwordEncode = passwordDecoder.getSavedPassword(user.password,password)
        val usersRole = getRolesByToken(token)
        return if(passwordEncode && usersRole.isEmpty())
            Pair(token, listOf(CompanyRole(role = UserRoles.CLIENT.name)))
        else if(passwordEncode) Pair(token, usersRole)
        else throw InvalidCredentials()
    }

    override fun getUserByToken(token: String?): UserEntity?{
        if(token == null) Unauthorized()
        val t = UUID.fromString(token)
        return userDao.getUserByToken(t) ?: throw InvalidToken()
    }

    fun getRolesByToken(token: String): List<CompanyRole> {
        val user = userDao.getUserByToken(UUID.fromString(token))?.id ?: throw UserNotFound()
        val companyRoleRep = userCompanyDao.getUserCompanyByUserId(user)
        if(companyRoleRep.isEmpty()) return emptyList()
        return companyRoleRep.map {
            CompanyRole(it.company!!.id,it.role)
        }
    }

    override fun addEmployees(companyId: Int, emails: List<String>) {
        val users = emails.map { email ->
            userDao.getUsersByEmail(email) ?: throw UserNotFound()
        }
        val company = companyDao.findAllById(companyId) ?: throw CompanyNotFound()

        val newEmployees = users.filter { user ->
            userCompanyDao.getRoleByCompanyAndUser(companyId, user.id) != UserRoles.MANAGER.name
                    && userCompanyDao.getRoleByCompanyAndUser(companyId, user.id) != UserRoles.EMPLOYEE.name
        }

        val savedEmployees = newEmployees.map { user ->
            UserCompany(user, company, UserRoles.EMPLOYEE.name)
        }

        val savedEntities = userCompanyDao.saveAll(savedEmployees)
        if (savedEntities.size != newEmployees.size) {
            throw Exception("Error saving new employees")
        }
    }


    override fun getAllAppointmentsByUser(token: String): AppointmentsUserInfo {
        val user = userDao.getUserByToken(UUID.fromString(token))?: throw UserNotFound()
        val listAppointment = appointmentDao.getAppointmentByUserDB(user.id)
        if(listAppointment.isEmpty()) return AppointmentsUserInfo()

        val currentDate = getCurrentDate()
        val currentTime = getCurrentTime()

        val (latterApp, soonerApp) = listAppointment.partition {
            it.appDate.before(currentDate) && it.appHour < currentTime
        }
        val a = soonerApp
        val b = latterApp
        return AppointmentsUserInfo(mapToAppointmentsInfo(soonerApp),mapToAppointmentsInfo(latterApp))
    }

    override fun mapToAppointmentsInfo(listAppointmentEntities: List<AppointmentEntity>):List<AppointmentInfo>{
        if(listAppointmentEntities.isEmpty()) return emptyList()
        return listAppointmentEntities.map {
            val getCompany = companyDao.getCompanyEntityBySchedule(it.schedule)?:throw CompanyNotFound()
            if(it.user == null) throw InvalidAppointment()
            val employee = it.user.firstOrNull { user ->
                user?.let { it1 -> userDao.getUserDBByIdAndRole(UserRoles.EMPLOYEE.name, getCompany.id, user.id) } != null ||
                user?.let { it1 -> userDao.getUserDBByIdAndRole(UserRoles.MANAGER.name, it1.id, user.id) } != null
            }?: throw UserNotFound()

            AppointmentInfo(
                it.id,
                convertToHourFormat(it.appHour),
                it.appDate,
                getCompany.name,
                employee.name
            )
        }
    }

    override fun getRoleByUserIdAndCompany(company: Int, user_id: String): String? {
        val token = userDao.getUserByToken(UUID.fromString(user_id))
        return if (token == null)  null
        else userCompanyDao.getRoleByCompanyAndUser(company, token.id)
    }

    override fun getEarnedMoneyEmployee(userId: Int, dateBegin: String?, dateEnd: String?,company: Int): Double {
        val user = userDao.getUserById(userId)?: throw UserNotFound()
        val getBeginDate = if(dateBegin == null) getCurrentDate() else Date.valueOf(dateBegin)
        val getEndDate = if(dateEnd == null) getLastDateIn30Days(getBeginDate) else  Date.valueOf(dateEnd)
        return servicesRepository.getEarnedMoneyByEmployee(user.id,company, getBeginDate, getEndDate) ?: 0.0
    }

    fun updateUserProfilePicture(id: Int, image: MultipartFile){
        val getUser = userDao.getUserById(id) ?: throw UserNotFound()
        val fileName = image.originalFilename?.let { StringUtils.cleanPath(it) }
        if(fileName?.contains("..")!!){
            println("not a valid file")
        }
        val encodedFile = image.bytes
        userDao.updateUserPicture(getUser.id,encodedFile)
    }

    fun validateAccount(email: String){
        userDao.changeStatusByEmail(email)
    }

    private fun getUserCoordinates(street: String,city: String,country: String):Geolocation{
        val getAddressInfo = AddressInformation(street,city,country)
        return GeoCoder().getGeolocation(getAddressInfo) ?: throw InvalidAddress()
    }

    fun rolesSerializer(companyRoles: List<CompanyRole>): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("[")
        for ((index, role) in companyRoles.withIndex()) {
            stringBuilder.append("{")
            stringBuilder.append("companyId:${role.companyId}|")
            stringBuilder.append("role:${role.role}")
            stringBuilder.append("}")
            if (index < companyRoles.size - 1) {
                stringBuilder.append("|")
            }
        }
        stringBuilder.append("]")
        return stringBuilder.toString()
    }

    fun rolesDeserializer(serializedRoles: String): List<CompanyRole> {
        val companyRoles = mutableListOf<CompanyRole>()
        val rolesWithoutBrackets = serializedRoles.substring(1, serializedRoles.length - 1)
        val roleStrings = rolesWithoutBrackets.split("|")
        for (roleString in roleStrings) {
            val roleWithoutBraces = roleString.substring(1, roleString.length - 1)
            val (companyIdStr, role) = roleWithoutBraces.split(":")
            val companyId = companyIdStr.substringAfter("companyId").toInt()
            companyRoles.add(CompanyRole(companyId, role))
        }
        return companyRoles
    }


}