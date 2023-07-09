package backend.jvm.model.company

import backend.jvm.model.image.ImageEntity
import backend.jvm.model.schedule.ScheduleEntity
import backend.jvm.model.service.ServiceEntity
import backend.jvm.model.UserCompany
import backend.jvm.utils.Geolocation
import jakarta.persistence.*
import javax.persistence.Column

@Entity(name = "CompanyEntity")
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

        @Column(name = "latitude")
        val latitude: Double

        @Column(name = "longitude")
        val longitude: Double

        @Column(name = "name")
        val name: String

        @Column(name = "phone")
        val phone: String

        @Column(name = "type")
        val type : String

        @Column(name = "description")
        val description:String

        @OneToMany(mappedBy = "company")
        val service: List<ServiceEntity>?

        @OneToOne(mappedBy = "company")
        val schedule: ScheduleEntity?

        @OneToMany(mappedBy = "company")
        val userCompany: List<UserCompany>?

        @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL])
        val entities: List<ImageEntity>?

        constructor() {
                this.nif = ""
                this.address = ""
                this.name = ""
                this.type = ""
                this.phone = ""
                this.description = ""
                this.latitude = 0.0
                this.longitude = 0.0
                this.service = null
                this.schedule = null
                this.userCompany = listOf()
                this.entities = listOf()
        }

        constructor(
            phone: String,
            coordinates: Geolocation,
            imageEntity:List<ImageEntity>?, nif: String, address: String, compName: String, compType: String, description: String, serviceEntity: List<ServiceEntity>?, schedule: ScheduleEntity?, usersDB: List<UserCompany>?){
                this.nif = nif
                this.phone = phone
                this.address = address
                this.name = compName
                this.type = compType
                this.description = description
                this.service = serviceEntity
                this.schedule = schedule
                this.userCompany = usersDB
                this.entities = imageEntity
                this.latitude = coordinates.latitude
                this.longitude = coordinates.longitude
        }

}







