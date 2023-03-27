package backend.domain

import jakarta.persistence.*
import javax.persistence.Column
import javax.persistence.OneToMany
import javax.persistence.OneToOne

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

        constructor() {
                this.nif = ""
                this.address = ""
                this.compName = ""
                this.compType = ""
                this.description = ""
                this.service = listOf()
                this.schedule = Schedule()
        }


        constructor(nif: String, phoneNumber: String, address: String, compName: String, compType: String, description: String, schedule: Schedule){
                this.nif = nif
                this.address = address
                this.compName = compName
                this.compType = compType
                this.description = description
                this.schedule = schedule
        }


        @OneToMany
        val calendar: MutableList<Calendar> = mutableListOf()

}