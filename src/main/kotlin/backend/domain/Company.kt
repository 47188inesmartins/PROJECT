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
        val id: Int? = null

        @Column(name = "nif")
        val nif: String = ""

        @Column(name = "phone_number")
        val phoneNumber: String = ""

        @Column(name = "address")
        val address: String = ""

        @Column(name = "comp_name")
        val compName: String = ""

        @Column(name = "comp_type")
        val compType : String = ""

        @Column(name = "description")
        val description:String = ""

        @OneToOne(mappedBy = "comp_id")
        val schedule: Schedule = Schedule()

        @OneToMany
        val calendar: MutableList<Calendar> = mutableListOf()

        fun getId(): Int? = this.id

        fun getNif():String = this.nif

        fun getPhoneNumber(): String = this.phoneNumber

        fun getAddress(): String = this.address

        fun getCompName(): String = this.compName

        fun getCompType(): String = this.compType

        fun getDescription(): String = this.description
}