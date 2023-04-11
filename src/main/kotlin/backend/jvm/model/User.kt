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
 * @property compId null if the user has the role of 'guest' or 'client'. the company where the user works
 */

@Entity
@Table(name = "sch_user")
class User {
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null

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
        val availability: String

        @ManyToOne
        @JoinColumn(name = "comp_id")
        val compId: Company?

        /*
        @ManyToMany
        @JoinTable(
                name = "user_role",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "role_id")]
        )
        val role: List<Role>?
        */

        @ManyToMany
        @JoinTable(
                name = "user_service",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "service_id")]
        )
        val services: List<Services>?

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
                this.compId = null
                this.appointment = null
        }

        constructor(
                email:String,
                password:String,
                clientName:String,
                birth:Date,
                serv: List<Services>,
                availability: String,
                company: Company,
                appointments: List<Appointment>
        ){
                this.token = UUID.randomUUID()
                this.email = email
                this.password = password
                this.name = clientName
                this.birthday = birth
                this.services = serv
                this.availability = AVAILABILITY_STATE
                this.compId = company
                this.appointment = appointments
        }

        constructor(
                email:String,
                password:String,
                clientName:String,
                birth:Date,
                serv: List<Services>,
                appointments: List<Appointment>
        ){
                this.token = UUID.randomUUID()
                this.email = email
                this.password = password
                this.name = clientName
                this.birthday = birth
                this.services = serv
                this.availability = AVAILABILITY_STATE
                this.compId = null
                this.appointment = appointments
        }

        companion object{
                const val AVAILABILITY_STATE = "none"
        }

}