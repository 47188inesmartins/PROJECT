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
 * @property company null if the user has the role of 'guest' or 'client'. the company where the user works
 */

@Entity
@Table(name = "sch_user")
@org.hibernate.annotations.NamedQuery(
        name = "getUserById",
        query = "from UserDB where id = :id",
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

        @Column(name = "availability")
        val availability: String?

        @ManyToMany
        @JoinTable(
                name = "user_role",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "role_name")]
        )
        val roles: List<Role>


        @ManyToOne
        @JoinColumn(name = "company_id")
        val company: Company?

        @ManyToMany
        @JoinTable(
                name = "user_service",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "service_id")]
        )
        val services: List<ServiceDB>?

        @ManyToMany
        @JoinTable(
                name = "appointment_user",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "appointment_id")]
        )
        val appointment: List<Appointment>?

        constructor(){
                this.token = UUID.randomUUID()
                this.email = ""
                this.password = ""
                this.name = ""
                this.birthday = Date.valueOf("2001-01-01")
                this.services = null
                this.availability = AVAILABILITY_STATE
                this.company = null
                this.appointment = null
                this.roles = listOf()
        }

        constructor(
                email:String,
                password:String,
                clientName:String,
                birth:Date,
                serv: List<ServiceDB>?,
                company: Company?,
                appointments: List<Appointment>?,
                roles: List<Role>
        ){
                this.token = UUID.randomUUID()
                this.email = email
                this.password = password
                this.name = clientName
                this.birthday = birth
                this.services = serv
                this.availability = AVAILABILITY_STATE
                this.company = company
                this.appointment = appointments
                this.roles = roles
        }

        constructor(
                email:String,
                password:String,
                clientName:String,
                birth:Date,
                serv: List<ServiceDB>?,
                appointments: List<Appointment>?,
                roles: List<Role>
        ){
                this.token = UUID.randomUUID()
                this.email = email
                this.password = password
                this.name = clientName
                this.birthday = birth
                this.services = serv
                this.availability = AVAILABILITY_STATE
                this.company = null
                this.appointment = appointments
                this.roles = roles
        }

        companion object{
                const val AVAILABILITY_STATE = "none"
        }

}