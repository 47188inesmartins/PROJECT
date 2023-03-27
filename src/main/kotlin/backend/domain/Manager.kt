package backend.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "manager")
class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
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

    @ManyToOne
    @JoinColumn(name = "comp_id")
    val compId: Company

    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(email:String,password:String,name:String,birthday: Date, company: Company) {
        if(!validateEmail(email)) throw Error("Invalid email") //FAZER UMA CENA DE ERROS
        this.email = email
        this.password = password
        this.name = name
        this.birthday = birthday
        this.compId = company
    }

    constructor(){
        this.email = ""
        this.password = ""
        this.name = ""
        this.birthday = java.sql.Date.valueOf("")
        this.compId = Company()
    }

    private fun validateEmail(email:String) = Regex("[a-zA-Z]+@[a-zA-Z]+").matches(email)

}