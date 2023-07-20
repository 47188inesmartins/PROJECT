package backend.jvm.model

import backend.jvm.model.service.ServiceEntity
import backend.jvm.model.user.UserEntity
import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "user_service")
class UserServiceEntity {

    @EmbeddedId
    var id: UserServicePK = UserServicePK()

    @ManyToOne
    @MapsId("user")
    @JoinColumn(name = "user_id")
    val user: UserEntity?

    @ManyToOne
    @MapsId("service")
    @JoinColumn(name = "service_id")
    val service: ServiceEntity?

    constructor(){
        this.user = null
        this.service = null
    }

    constructor(user: UserEntity, serviceEntity: ServiceEntity){
        this.user = user
        this.service = serviceEntity
    }
}

@Embeddable
class UserServicePK() : Serializable {

    var user: Int = 0
    var service: Int = 0

    constructor(user: Int, service: Int) : this() {
        this.user = user
        this.service = service
    }

}