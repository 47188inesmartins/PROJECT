package backend.jvm.model

import jakarta.persistence.*
import javax.persistence.Column


@Entity
@Table(name = "images_company")
class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column(name = "image", columnDefinition = "BYTEA")
    var image: ByteArray?

    @ManyToOne
    @JoinColumn(name = "company_id")
    val company: Company?

    constructor(){
        this.image = null
        this.company = null
    }

    constructor(image: ByteArray?, company: Company) {
        this.image = image
        this.company = company
    }
}




