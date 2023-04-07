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
    val user : User?

    constructor(){
        this.name = ""
        this.user = null
       // this.user = listOf()
    }

    constructor(name : String , user: User){
        this.name = name
        this.user = user
       // this.user = user
    }
}