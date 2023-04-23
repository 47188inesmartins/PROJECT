package backend.jvm.model

import jakarta.persistence.*

@Entity
@Table(name = "u_role")
class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int? = null

    @Column(name = "name")
    val name: String

    @ManyToOne
    val userDB : UserDB?

    constructor(){
        this.name = ""
        this.userDB = null
       // this.user = listOf()
    }

    constructor(name : String, userDB: UserDB){
        this.name = name
        this.userDB = userDB
       // this.user = user
    }
}