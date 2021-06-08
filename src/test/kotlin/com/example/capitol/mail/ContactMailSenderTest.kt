package com.example.capitol.mail

import com.example.capitol.entity.Contact
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSenderImpl



internal class ContactMailSenderTest (){


    //check mailtrap to see if this is working
    //straight copy of ContactMailSender content
    @Test
    fun testSendContact(){
        var mailSender: JavaMailSenderImpl = JavaMailSenderImpl()
        mailSender.host = "smtp.mailtrap.io"
        mailSender.port = 587
        mailSender.username = "a9600b5c0c643b"
        mailSender.password = "9fbe31d31f4f12"
        mailSender.testConnection()

        val message = SimpleMailMessage()
        //message.setTo("AlecMcCaulDevelopment@gmail.com")
        message.setTo("alecmccauldevelopment@gmail.com")

        message.setSubject("New contact from email@address.com")
        message.setText("whatever he has to say")
        message.setFrom("Nameman@Namington")
        mailSender.send(message)
    }
    //TODO figure out a way to get Environment variable in
    @Test
    fun testSendContact2 (){
        //val mailSender:ContactMailSender = new ContactMailSender(environment);

    }
}
