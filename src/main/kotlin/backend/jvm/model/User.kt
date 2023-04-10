package backend.jvm.model

import jakarta.persistence.*
import java.sql.Date
import java.util.UUID

/**
 *
 * User entity
 *
 * @property id the User primary key
 * @property token the User token
 * @property email the User email
 * @property password the User password
 * @property username the User username
 * @property clientName the User name
 * @property birthday the User birthday
 * @property companyId null if the user has the role of 'guest' or 'client'. the company where the user works
 */

@Entity
@Table(name = "sch_user")
class User {
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null

        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "token")
        var token: UUID? = null

        @Column(name = "email")
        val email: String

        @Column(name = "password")
        var password: String

        @Column(name = "name")
        val clientName: String

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
                this.email = ""
                this.password = ""
                this.clientName = ""
                this.birthday = Date.valueOf("2001-01-01")
                this.services = null
                this.availability = ""
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
                this.email = email
                this.password = password
                this.clientName = clientName
                this.birthday = birth
                this.services = serv
                this.availability = availability
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
                this.email = email
                this.password = password
                this.clientName = clientName
                this.birthday = birth
                this.services = serv
                this.availability = "none"
                this.compId = null
                this.appointment = appointments
        }

}