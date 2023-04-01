package backend.jvm.model

import jakarta.persistence.*
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
@Table(name = "company")
class Company {

        @Id
        @Column(name = "id")
        var id: Int? = null

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

       /* @OneToMany(mappedBy = "cid")
        val service: List<Service>

        @OneToOne(mappedBy = "compIdSch")
        val schedule: Schedule?*/

       /* @OneToMany(mappedBy = "compId")
        val manager: List<Manager>*/

        constructor() {
                this.nif = ""
                this.address = ""
                this.name = ""
                this.type = ""
                this.description = ""
                //this.service = listOf()
                // this.schedule = Schedule()
                // this.manager = listOf()
        }

        constructor(id: Int, nif: String, address: String, compName: String, compType: String, description: String,/* service: List<Service>, schedule: Schedule?*//*, manager: List<Manager>*/){
               this.id = id
                this.nif = nif
                this.address = address
                this.name = compName
                this.type = compType
                this.description = description
               // this.service = service
                //this.schedule = schedule
             //   this.manager = manager
        }
}

