package backend.domain

import jakarta.persistence.*

@Entity
@Table(name = "role")
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    var id: Int? = null

    @Column(name = "name")
    val name: String

    @ManyToMany(mappedBy = "role")
    val user : List<User>

    constructor(){
        this.name = ""
        this.user = listOf()
    }

    constructor(name : String, user: List<User>){
        this.name = name
        this.user = user
    }
}