package backend.jvm.model

import jakarta.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "company")
@org.hibernate.annotations.NamedQuery(
        name = "getCompanyById",
        query = "from Company where id = :id",
        cacheable = true,
        timeout = 1
)
class Company {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0

        @Column(name = "nif", unique = true)
        val nif: String

        @Column(name = "address")
        val address: String

        @Column(name = "name")
        val name: String

        @Column(name = "type")
        val type : String

        @Column(name = "description")
        val description:String

        @OneToMany(mappedBy = "company")
        val serviceDBS: List<ServiceDB>?

        @OneToOne(mappedBy = "company")
        val schedule: Schedule?

        /*@OneToMany(mappedBy = "company")
        val userDBS: List<UserDB>?*/

        @ManyToMany
        @JoinTable(
                name = "user_role",
                joinColumns = [JoinColumn(name = "company_id")],
                inverseJoinColumns = [JoinColumn(name = "role_name")]
        )
        val roles: List<Role>?
        constructor() {
                this.nif = ""
                this.address = ""
                this.name = ""
                this.type = ""
                this.description = ""
                this.serviceDBS = null
                this.schedule = null
                this.roles = null
        }

        constructor(nif: String, address: String, compName: String, compType: String, description: String, serviceDB: List<ServiceDB>?, schedule: Schedule?, role: List<Role>?){
                this.nif = nif
                this.address = address
                this.name = compName
                this.type = compType
                this.description = description
                this.serviceDBS = serviceDB
                this.schedule = schedule
                this.roles = role
        }
}

