package backend.jvm.model

import jakarta.persistence.*
/*
@Entity
@Table(name = "user_role")
class UserRole {

    @EmbeddedId
    var id: UserRolePK? = null

    @ManyToOne
    @MapsId("user")
    @JoinColumn(name = "user_id")
    private val userDB: UserDB?

    @ManyToOne
    @MapsId("role")
    @JoinColumn(name = "role_name")
    private val disciplina: Role?

    @Column(name = "company_id")
    private val companyId: Int?

    constructor() {
        userDB = null
        disciplina = null
        companyId = null
    }

}

@Embeddable
class UserRolePK {
    var user: Int = 0
    var role: String = ""
}
*/