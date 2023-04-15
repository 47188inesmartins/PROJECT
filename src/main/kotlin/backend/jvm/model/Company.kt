package backend.jvm.model

import jakarta.persistence.*
import javax.persistence.Column

@Entity
@Table(name = "company")
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

        @OneToMany(mappedBy = "companyId")
        val serviceDBS: List<ServiceDB>?

        @OneToOne(mappedBy = "companyId")
        val schedule: Schedule?

        @OneToMany(mappedBy = "companyId")
        val users: List<User>?

        constructor() {
                this.nif = ""
                this.address = ""
                this.name = ""
                this.type = ""
                this.description = ""
                this.serviceDBS = null
                this.schedule = null
                this.users = null
        }

        constructor(nif: String, address: String, compName: String, compType: String, description: String, serviceDBS: List<ServiceDB>, schedule: Schedule?, users: List<User>){
                this.nif = nif
                this.address = address
                this.name = compName
                this.type = compType
                this.description = description
                this.serviceDBS = serviceDBS
                this.schedule = schedule
                this.users = users
        }
}

