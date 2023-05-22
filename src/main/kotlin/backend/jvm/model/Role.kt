package backend.jvm.model

import jakarta.persistence.*

@Entity
@Table(name = "u_role")
class Role {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int? = null*/

    @Id
    @Column(name = "name")
    val name: String

    @OneToMany(mappedBy = "role_name")
    private var users: List<UserRole?>?


    constructor(){
        this.name = ""
     //   this.userDB = null
       // this.user = listOf()
    }

    constructor(name : String){
        this.name = name
      //  this.userDB = userDB
       // this.user = user
    }
}