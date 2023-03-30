package backend.jvm.model

import jakarta.persistence.*
import javax.persistence.Column

/*
@Entity
@Table(name = "COMPANY_NUMBERS")
class  CompanyNumbers{
        @Id
        @Column(name = "phone_number")
        val phoneNumber:String

        @Id
        @Column(name = "cid")
        @ManyToOne
        val companyId: Company

        constructor(phoneNumber:String,cid: Company){
                this.phoneNumber = phoneNumber
                this.companyId = cid
        }

        constructor(){
                this.phoneNumber = ""
                this.companyId = Company()
        }

}*/