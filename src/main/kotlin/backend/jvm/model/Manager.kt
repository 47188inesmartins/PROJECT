package backend.jvm.model

import jakarta.persistence.*
import java.util.*
import java.util.Date

/*
@Entity
@Table(name = "manager")
class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int? = null
    //@GeneratedValue(strategy = GenerationType.UUID)

    @Column(name = "token")
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
    val compId: Company?

    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(email:String, password:String, name:String, birthday: Date, company: Company) {
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
        this.birthday = Date()
        this.compId = null
    }

    private fun validateEmail(email:String) = Regex("[a-zA-Z]+@[a-zA-Z]+").matches(email)

}*/