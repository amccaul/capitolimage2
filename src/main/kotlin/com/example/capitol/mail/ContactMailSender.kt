package com.example.capitol.mail

import com.sun.xml.messaging.saaj.packaging.mime.MessagingException
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Component
//TODO get mail service from Java to work in Kotlin
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.lang.NumberFormatException
import javax.mail.*
//import org.springframework.mail.MailSender
@Component
class ContactMailSender (environment : Environment): ContactSender  {
    private val logger = LoggerFactory.getLogger(javaClass)

    var mailSender: JavaMailSenderImpl = JavaMailSenderImpl()

    init{
        mailSender.host = environment.getProperty("spring.mail.host")
        //TODO figure out what's wrong with this toInt()
        //mailSender.port = environment.getProperty("spring.mail.port").toInt()
        mailSender.port = 587
        mailSender.username = environment.getProperty("spring.mail.username")
        mailSender.password = environment.getProperty("spring.mail.password")

        /*
        try {
            mailSender.testConnection()
        } catch (e:MessagingException){
            logger.info("Connection to email server failed:"+e.message)
        }
*/
    }

    override fun sendContact(from: String, name: String, feedback: String):Unit{
         val message = SimpleMailMessage()
        //message.setTo("AlecMcCaulDevelopment@gmail.com")
        message.setTo("alecmccauldevelopment@gmail.com")

        message.setSubject("New contact from $name")
        message.setText(feedback)
        message.setFrom(from)
        mailSender.send(message)
        /* TODO do something with catch block
        try {
            mailSender.send(message)
        }catch (e: MailException){}
        */
    }
}
