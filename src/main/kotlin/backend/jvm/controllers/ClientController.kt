package backend.jvm.controllers

import backend.jvm.model.Client
import backend.jvm.services.ClientServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping(("/client"))
class ClientController {

     @Autowired
     lateinit var clientServices: ClientServices

     @ResponseBody
     @PostMapping
     fun addClient(client: Client): Client {
        return clientServices.addClient(client)
     }

     @DeleteMapping("/{id}")
     fun deleteClient(id: Int){
         return clientServices.deleteClient(id)
     }

     @GetMapping("/{id}")
     fun getClientById(id: Int): Optional<Client> {
         return clientServices.getClientById(id)
     }

     @GetMapping("/email")
     fun getClientByEmail(@RequestParam email: String): Client {
         return clientServices.getClientByEmail(email)
     }

     @GetMapping("/token")
     fun getClientByToken(@RequestParam token: UUID): Client {
         return clientServices.getClientByToken(token)
     }
}