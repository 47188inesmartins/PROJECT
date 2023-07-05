package backend.jvm.services

import backend.jvm.utils.emails.EmailMessages
import jakarta.mail.MessagingException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service


@Service
class EmailSenderService {

    @Autowired
    lateinit var javaMailSender: JavaMailSender

    @Throws(MessagingException::class)
    fun sendValidationEmail(recipientEmail: String) {
        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        val emailContent = EmailMessages.ValidateAccount(recipientEmail)
        helper.setTo(recipientEmail)
        helper.setSubject("Activate account")
        helper.setText(emailContent, true)
        javaMailSender.send(message)
    }
}