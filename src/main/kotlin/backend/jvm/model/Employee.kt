package backend.jvm.model

import jakarta.persistence.*
import java.util.*
import javax.persistence.Column

@Entity
@Table(name = "employee")
class Employee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null

    @Column(name = "token")
    @GeneratedValue(strategy = GenerationType.UUID)
    var token: String? = null

    @Column(name = "email")
    val email: String

    @Column(name = "password")
    val password:String

    @Column(name = "name")
    val name:String

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    val birthday: Date

    @ManyToMany
    @JoinTable(
        name = "employee_service",
        joinColumns = [JoinColumn(name = "employee_id")],
        inverseJoinColumns = [JoinColumn(name = "service_id")]
    )
    val services: List<Services>?

    @OneToMany(mappedBy = "eid")
    val appointment: List<Appointment>?


    @ManyToOne
    @JoinColumn(name = "comp_id")
    val company: Company?


    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(email:String, password:String, name:String, birthday: Date, services: List<Services>, appointment: List<Appointment>, company: Company) {
        if(!validateEmail(email)) throw Error("Invalid email") //FAZER UMA CENA DE ERROS
        this.email = email
        this.password = password
        this.name = name
        this.birthday = birthday
        this.services = services
        this.appointment = appointment
        this.company = company
    }

    constructor(){
        this.email = ""
        this.password = ""
        this.name = ""
        this.birthday = java.sql.Date.valueOf("2001-01-01")
        this.services = listOf()
        this.appointment = listOf()
        this.company = null
    }


    private fun validateEmail(email:String) = Regex("[a-zA-Z]+@[a-zA-Z]+").matches(email)
}