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

        @OneToMany(mappedBy = "company")
        val serviceDBS: List<ServiceDB>?

        @OneToOne(mappedBy = "company")
        val schedule: Schedule?

        @OneToMany(mappedBy = "company")
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

        constructor(nif: String, address: String, compName: String, compType: String, description: String, serviceDB: List<ServiceDB>?, schedule: Schedule?, users: List<User>?){
                this.nif = nif
                this.address = address
                this.name = compName
                this.type = compType
                this.description = description
                this.serviceDBS = serviceDB
                this.schedule = schedule
                this.users = users
        }
}

