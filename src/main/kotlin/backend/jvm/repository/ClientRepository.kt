package backend.jvm.repository
import backend.jvm.model.Client
import org.springframework.data.jpa.repository.JpaRepository


interface ClientRepository : JpaRepository<Client, Int>{

}