package backend.jvm.model

import backend.jvm.model.company.CompanyEntity
import jakarta.persistence.*
import javax.persistence.Column


@Entity
@Table(name = "COMPANY_NUMBERS")
class  CompanyNumbers{
        @Id
        @Column(name = "phone_number")
        val phoneNumber:String

        @Id
        @Column(name = "company_id")
        @ManyToOne
        val companyEntityId: CompanyEntity?

        constructor(phoneNumber:String,cid: CompanyEntity){
                this.phoneNumber = phoneNumber
                this.companyEntityId = cid
        }

        constructor(){
                this.phoneNumber = ""
                this.companyEntityId = null
        }

}