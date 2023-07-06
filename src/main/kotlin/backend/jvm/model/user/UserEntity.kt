package backend.jvm.model.user

import backend.jvm.model.Role
import backend.jvm.model.unavailability.UnavailabilityEntity
import backend.jvm.model.UserCompany
import backend.jvm.model.appointment.AppointmentEntity
import backend.jvm.model.service.ServiceEntity
import backend.jvm.utils.Geolocation
import jakarta.persistence.*
import java.sql.Date
import java.util.*


/**
 *
 * User entity
 *
 * @property id the User primary key
 * @property token the user token
 * @property email the user email
 * @property password the User password
 * @property name the user's name
 * @property birthday the User birthday
 *
 *
*/

@Entity(name = "UserEntity")
@Table(name = "sch_user")
@org.hibernate.annotations.NamedQuery(
        name = "getUserById",
        query = "from UserEntity where id = :id",
        cacheable = true,
        timeout = 1
)

@org.hibernate.annotations.NamedQuery(
        name = "getUserByToken",
        query = "from UserEntity where token = :token",
        cacheable = true,
        timeout = 1
)

class UserEntity {

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0

        @Column(name = "token", unique = true)
        var token: UUID

        @Column(name = "email", unique = true)
        val email: String

        @Column(name = "password")
        var password: String

        @Column(name = "name")
        val name: String

        @Column(name = "birthday")
        val birthday: Date

        @Column(name = "interests")
        val interests: String

        @Column(name = "address")
        val address: String

        @Column(name = "latitude")
        val latitude: Double

        @Column(name = "longitude")
        val longitude: Double

        @Column(name = "profile_pic", columnDefinition = "BYTEA")
        val profilePic: ByteArray?

        @Column(name = "status")
        val status: String

        @OneToMany(mappedBy = "user")
        val unavailability: List<UnavailabilityEntity>?

        @ManyToMany
        @JoinTable(
                name = "user_role",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "role_name")]
        )
        val roles: List<Role>

        @ManyToMany
        @JoinTable(
                name = "user_service",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "service_id")]
        )
        val services: List<ServiceEntity>?

        @OneToMany(mappedBy = "user")
        val companies: List<UserCompany>?

        @ManyToMany(mappedBy = "user")
        val appointment: List<AppointmentEntity>?

        constructor() {
                this.token = UUID.randomUUID()
                this.email = ""
                this.password = ""
                this.name = ""
                this.birthday = Date.valueOf("2001-01-01")
                this.services = null
                this.address = " "
                this.appointment = null
                this.roles = listOf()
                this.unavailability = listOf()
                this.companies = listOf()
                this.interests = ""
                this.profilePic = null
                this.status = "PENDING"
                this.latitude = 0.0
                this.longitude = 0.0
        }

        constructor(
                coordinates: Geolocation,
                email: String,
                password: String,
                clientName: String,
                birth: Date,
                serv: List<ServiceEntity>?,
                appointmentEntities: List<AppointmentEntity>?,
                roles: List<Role>,
                unavailabilityEntity: List<UnavailabilityEntity>?,
                companies: List<UserCompany>?,
                interests: String,
                address: String,
                profilePic: ByteArray?,
                status: String
        ) {
                this.token = UUID.randomUUID()
                this.email = email
                this.password = password
                this.name = clientName
                this.birthday = birth
                this.services = serv
                this.address = address
                this.appointment = appointmentEntities
                this.roles = roles
                this.unavailability = unavailabilityEntity
                this.companies = companies
                this.interests = interests
                this.profilePic = profilePic
                this.status = status
                this.latitude = coordinates.latitude
                this.longitude = coordinates.longitude
        }
}