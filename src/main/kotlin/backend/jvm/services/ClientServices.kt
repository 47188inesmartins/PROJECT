package backend.jvm.services

/*
import backend.jvm.model.Client
import backend.jvm.repository.ClientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientServices {

    @Autowired
    lateinit var clientRepository: ClientRepository

    fun addClient(client: Client): Client{
        return clientRepository.save(client)
    }

    fun deleteClient(id: Int){
        return clientRepository.deleteById(id)
    }

    fun getClientById(id: Int): Optional<Client> {
        return clientRepository.findById(id)
    }

    fun getClientByEmail(email: String): Client{
        return clientRepository.getClientByEmail(email)
    }

    fun getClientByToken(token: UUID): Client{
        return clientRepository.getClientByToken(token)
    }

}*/