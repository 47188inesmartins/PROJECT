package backend.jvm.model

import backend.jvm.model.user.UserEntity
import jakarta.persistence.*
/*
@Entity
@Table(name = "u_role")
class Role {

    @Id
    @Column(name = "name")
    val name: String

    @ManyToMany(mappedBy = "roles")
    private var users: List<UserEntity?>?

    constructor(){
        this.name = ""
        this.users = null
    }

    constructor(name : String){
        this.name = name
        this.users = null
    }
}*/