package backend.jvm.model

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "user_company")
class UserCompany {

    @EmbeddedId
    var id: UserCompanyPK = UserCompanyPK()

    @ManyToOne
    @MapsId("user")
    @JoinColumn(name = "user_id")
    val user: UserDB?

    @ManyToOne
    @MapsId("company")
    @JoinColumn(name = "company_id")
    val company: Company?

    val role: String

    constructor(){
        this.user = null
        this.company = null
        this.role = ""
    }

    constructor(user: UserDB, company: Company, role: String){
        this.user = user
        this.company = company
        this.role = role
    }


}


@Embeddable
class UserCompanyPK() : Serializable {

   // @Column(name = "user_id", insertable=false, updatable=false)
    var user: Int = 0
   // @Column(name = "company_id", insertable=false, updatable=false)
    var company: Int = 0

    constructor(user: Int, company: Int) : this() {
        this.user = user
        this.company = company
    }

}