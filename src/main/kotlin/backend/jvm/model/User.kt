package backend.jvm.model

import jakarta.persistence.*
import java.util.*

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

        @Column(name = "token")
        val token: String? = null

        @Column(name = "email")
        val email: String

        @Column(name = "password")
        val password: String

        @Column(name = "name")
        val clientName: String

        @Column(name = "birthday")
        val birthday: Date

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
                name = "app_user",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "app_id")]
        )
        val appointment: List<Appointment>?


        constructor(){
                this.email = ""
                this.password = ""
                this.clientName = ""
                this.birthday = java.sql.Date.valueOf("2001-01-01")
                this.services = null
                this.availability = ""
                this.compId = null
                this.appointment = null
        }

        constructor(email:String,
                    password:String,
                    clientName:String,
                    birth:Date,
                    serv : List<Services>,
                    availability: String,
                    company : Company,
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

}