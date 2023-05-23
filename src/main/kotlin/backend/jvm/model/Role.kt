package backend.jvm.model

import jakarta.persistence.*

@Entity
@Table(name = "u_role")
class Role {

    @Id
    @Column(name = "name")
    val name: String

    @ManyToMany(mappedBy = "roles")
    private var users: List<UserDB?>?

    constructor(){
        this.name = ""
        this.users = null
    }

    constructor(name : String){
        this.name = name
        this.users = null
    }
}