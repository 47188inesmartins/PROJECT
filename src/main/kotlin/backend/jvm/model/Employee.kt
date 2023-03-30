package backend.jvm.model

import jakarta.persistence.*
import java.util.*
import javax.persistence.Column

/*@Entity
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
    val service: List<Service>

    @OneToMany(mappedBy = "eid")
    val appointment: List<Appointment>

    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(email:String, password:String, name:String, birthday: Date, service: List<Service>, appointment: List<Appointment>) {
        if(!validateEmail(email)) throw Error("Invalid email") //FAZER UMA CENA DE ERROS
        this.email = email
        this.password = password
        this.name = name
        this.birthday = birthday
        this.service = service
        this.appointment = appointment
    }

    constructor(){
        this.email = ""
        this.password = ""
        this.name = ""
        this.birthday = java.sql.Date.valueOf("")
        this.service = listOf()
        this.appointment = listOf()
    }


    private fun validateEmail(email:String) = Regex("[a-zA-Z]+@[a-zA-Z]+").matches(email)*/
//}