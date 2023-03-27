package backend.model.repository
import backend.domain.User
import org.springframework.boot.autoconfigure.integration.IntegrationProperties.RSocket.Client
import org.springframework.data.jpa.repository.JpaRepository

interface ClientRepository : JpaRepository<Client, Int>{

}