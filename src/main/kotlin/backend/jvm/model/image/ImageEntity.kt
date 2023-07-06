package backend.jvm.model.image

import backend.jvm.model.company.CompanyEntity
import jakarta.persistence.*
import javax.persistence.Column


@Entity
@Table(name = "images_company")
class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column(name = "image", columnDefinition = "BYTEA")
    var image: ByteArray?

    @ManyToOne
    @JoinColumn(name = "company_id")
    val company: CompanyEntity?

    constructor(){
        this.image = null
        this.company = null
    }

    constructor(image: ByteArray?, companyEntity: CompanyEntity) {
        this.image = image
        this.company = companyEntity
    }
}




