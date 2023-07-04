package backend.jvm.controllers

import backend.jvm.services.EmailSenderRequest
import backend.jvm.services.EmailSenderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class EmailController {

    @Autowired
    lateinit var emailSenderService: EmailSenderService

    @PostMapping("/send-email")
    fun sendEmail(@RequestBody emailRequest: EmailSenderRequest) {
        emailSenderService.sendEmail(emailRequest.to,emailRequest.text,emailRequest.subject)
    }
}