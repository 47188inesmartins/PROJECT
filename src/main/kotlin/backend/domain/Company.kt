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

        @Column(name = "phone_number")
        val phoneNumber: String

        @Column(name = "address")
        val address: String

        @Column(name = "comp_name")
        val compName: String

        @Column(name = "comp_type")
        val compType : String

        @Column(name = "description")
        val description:String

        @OneToOne(mappedBy = "comp_id")
        val schedule: Schedule

        constructor() {
                this.nif = ""
                this.phoneNumber = ""
                this.address = ""
                this.compName = ""
                this.compType = ""
                this.description = ""
                this.schedule = Schedule()
        }


        constructor(nif: String, phoneNumber: String, address: String, compName: String, compType: String, description: String, schedule: Schedule){
                this.nif = nif
                this.phoneNumber = phoneNumber
                this.address = address
                this.compName = compName
                this.compType = compType
                this.description = description
                this.schedule = schedule
        }


        @OneToMany
        val calendar: MutableList<Calendar> = mutableListOf()

}