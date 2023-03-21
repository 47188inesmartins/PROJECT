package backend.domain

import jakarta.persistence.*

@Entity
@Table(name = "company")
class Company{
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        val id: Int? = null

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


        constructor(){
                this.nif = ""
                this.compName = ""
                this.compType = ""
                this.address = ""
                this.description = ""
                this.phoneNumber = ""

        }

}