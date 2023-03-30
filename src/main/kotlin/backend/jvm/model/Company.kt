package backend.jvm.model

import jakarta.persistence.*
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.OneToMany

@Entity
@Table(name = "company")
class Company {

        @Id
        @Column(name = "id")
        var id: Int? = null

        @Column(name = "nif")
        val nif: String

        @Column(name = "address")
        val address: String

        @Column(name = "comp_name")
        val compName: String

        @Column(name = "comp_type")
        val compType : String

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
                this.compName = ""
                this.compType = ""
                this.description = ""
                this.id = 0
              //  this.service = listOf()
               // this.schedule = Schedule()
               // this.manager = listOf()
        }

        constructor(nif: String, address: String, compName: String, compType: String, description: String, id: Int/* service: List<Service>, schedule: Schedule?*//*, manager: List<Manager>*/){
                this.nif = nif
                this.address = address
                this.compName = compName
                this.compType = compType
                this.description = description
                this.id = id
               // this.service = service
                //this.schedule = schedule
             //   this.manager = manager
        }
}

