package backend.domain

import jakarta.persistence.*
import javax.persistence.Column
import javax.persistence.OneToMany

@Entity
@Table(name = "company")
class Company {

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

        @OneToMany(mappedBy = "cid")
        val service: List<Service>

        @OneToOne(mappedBy = "cid")
        val schedule: Schedule

        @OneToMany(mappedBy = "comp_id")
        val manager: List<Manager>

        constructor() {
                this.nif = ""
                this.address = ""
                this.compName = ""
                this.compType = ""
                this.description = ""
                this.service = listOf()
                this.schedule = Schedule()
                this.manager = listOf()
        }

        constructor(nif: String,address: String, compName: String, compType: String, description: String, service: List<Service>, schedule: Schedule, manager: List<Manager>){
                this.nif = nif
                this.address = address
                this.compName = compName
                this.compType = compType
                this.description = description
                this.service = service
                this.schedule = schedule
                this.manager = manager
        }
}