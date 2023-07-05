package backend.jvm.model.company

import backend.jvm.model.image.ImageEntity
import backend.jvm.model.schedule.ScheduleEntity
import backend.jvm.model.service.ServiceEntity
import backend.jvm.model.UserCompany
import jakarta.persistence.*
import javax.persistence.Column

@Entity(name = "Company")
@Table(name = "company")
@org.hibernate.annotations.NamedQuery(
        name = "getCompanyById",
        query = "from CompanyEntity where id = :id",
        cacheable = true,
        timeout = 1
)
class CompanyEntity {

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


        //@Lob
       /* @Column(name = "url", columnDefinition = "BYTEA")
        val url: ByteArray?*/

        @OneToMany(mappedBy = "companyEntity")
        val serviceEntities: List<ServiceEntity>?

        @OneToOne(mappedBy = "companyEntity")
        val schedule: ScheduleEntity?

        @OneToMany(mappedBy = "companyEntity")
        val userCompany: List<UserCompany>?

        @OneToMany(mappedBy = "companyEntity", cascade = [CascadeType.ALL])
        val entities: List<ImageEntity>?

        constructor() {
                this.nif = ""
                this.address = ""
                this.name = ""
                this.type = ""
                this.description = ""
                this.serviceEntities = null
                this.schedule = null
                this.userCompany = listOf()
                this.entities = listOf()
        }

        constructor(imageEntity:List<ImageEntity>?, nif: String, address: String, compName: String, compType: String, description: String, serviceEntity: List<ServiceEntity>?, schedule: ScheduleEntity?, usersDB: List<UserCompany>?){
                this.nif = nif
                this.address = address
                this.name = compName
                this.type = compType
                this.description = description
                this.serviceEntities = serviceEntity
                this.schedule = schedule
                this.userCompany = usersDB
                this.entities = imageEntity
        }

}







