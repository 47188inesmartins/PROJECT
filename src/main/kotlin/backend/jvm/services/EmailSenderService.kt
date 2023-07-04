package backend.jvm.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailSenderService {

    @Autowired
    lateinit var javaMailSender: JavaMailSender

    fun sendEmail(email:String,body:String,subject:String){

        val mimeMessage = javaMailSender.createMimeMessage()
        val mimeMessageHelper = MimeMessageHelper(mimeMessage,true)
        mimeMessageHelper.setFrom("scheduleitapp1@gmail.com")
        mimeMessageHelper.setTo(email)
        mimeMessageHelper.setText(body)
        mimeMessageHelper.setSubject(subject)

        javaMailSender.send(mimeMessage)
    }
}