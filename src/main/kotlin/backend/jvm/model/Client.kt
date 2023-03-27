package backend.jvm.model

import jakarta.persistence.*
import java.sql.Date
import javax.persistence.Column


@Entity
@Table(name = "client")
class Client {
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

    @OneToMany(mappedBy = "cid")
    val appointment: List<Appointment>

    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(email:String, password:String, name:String, birthday:Date, appointment: List<Appointment>) {
        if(!validateEmail(email)) throw Error("Invalid email")//FAZER UMA CENA DE ERROS
        this.email = email
        this.password = password
        this.name = name
        this.birthday = birthday
        this.appointment = appointment
    }

    constructor(){
        this.email = ""
        this.password = ""
        this.name = ""
        this.birthday = Date.valueOf("")
        this.appointment = listOf()
    }


    private fun validateEmail(email:String) = Regex("[a-zA-Z]+@[a-zA-Z]+").matches(email)

}