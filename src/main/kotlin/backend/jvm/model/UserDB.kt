package backend.jvm.model

import jakarta.persistence.*
import java.sql.Date
import java.util.UUID

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

@Entity
@Table(name = "sch_user")
@org.hibernate.annotations.NamedQuery(
        name = "getUserById",
        query = "from UserDB where id = :id",
        cacheable = true,
        timeout = 1
)

@org.hibernate.annotations.NamedQuery(
        name = "getUserByToken",
        query = "from UserDB where token = :token",
        cacheable = true,
        timeout = 1
)

class UserDB {
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

        @OneToMany(mappedBy = "userDB")
        val unavailabilityDB : List<UnavailabilityDB>?

        @Column(name = "interests")
        val interests : String

        @Column(name = "address")
        val address : String

        @Column(name = "profile_pic", columnDefinition = "BYTEA")
        val profilePic: ByteArray?

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
        val services: List<ServiceDB>?

        @OneToMany(mappedBy = "user")
        val companies: List<UserCompany>?

        @ManyToMany(mappedBy = "usersDB")
        val appointment: List<Appointment>?

        constructor(){
                this.token = UUID.randomUUID()
                this.email = ""
                this.password = ""
                this.name = ""
                this.birthday = Date.valueOf("2001-01-01")
                this.services = null
                this.address = " "
                this.appointment = null
                this.roles = listOf()
                this.unavailabilityDB = listOf()
                this.companies = listOf()
                this.interests = ""
                this.profilePic = null
        }

        constructor(
                email:String,
                password:String,
                clientName:String,
                birth:Date,
                serv: List<ServiceDB>?,
                appointments: List<Appointment>?,
                roles: List<Role>,
                unavailabilityDB: List<UnavailabilityDB>?,
                companies: List<UserCompany>?,
                interests: String,
                address: String,
                profilePic: ByteArray?
        ){
                this.token = UUID.randomUUID()
                this.email = email
                this.password = password
                this.name = clientName
                this.birthday = birth
                this.services = serv
                this.address = address
                this.appointment = appointments
                this.roles = roles
                this.unavailabilityDB = unavailabilityDB
                this.companies = companies
                this.interests = interests
                this.profilePic = profilePic
        }
}