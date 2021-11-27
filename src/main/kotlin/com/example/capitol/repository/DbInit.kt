package com.example.capitol.repository

import com.example.capitol.entity.CapitolImage
import com.example.capitol.entity.CapitolUser
import com.example.capitol.service.CapitolImageService
import org.springframework.boot.CommandLineRunner
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Service
//TODO delete this in production
//@ComponentScan("com.example.capitol.config")
class DbInit(var capitolUserRepository: CapitolUserRepository,
             var capitolImageService: CapitolImageService,
             var passwordEncoder:PasswordEncoder
             ):CommandLineRunner {
    override fun run(vararg args: String?) {
        try {
            capitolImageService.deleteAll()
        } catch (e : Exception){}
        capitolUserRepository.deleteAll()

        var password = passwordEncoder.encode("pass123")
        var u = CapitolUser(username="u", password = password)
                //passwordEncoder.encode("{noop}pass123"))
                u.role="USER"
        capitolUserRepository.save(u)



        var a = CapitolUser(username="admin@123.com",
            password = password)
        a.role="ADMIN"
        capitolUserRepository.save(a)

        var u2 = capitolUserRepository.findByUsername("u") as CapitolUser
        var i1 = CapitolImage(image_Id = 3, capitolUser = u2, image_name = "Chad.jpg", url = "C:\\Users\\Alec\\Pictures\\output\\u\\3.jpg");

        var byteArray: ByteArray = File("C:\\Users\\Alec\\Pictures\\output\\chad.jpg").readBytes()
        var multipart : MultipartFile = MockMultipartFile("chad.jpg", "chad.jpg", MediaType.IMAGE_JPEG.type, byteArray)
        capitolImageService.store(multipart, u2)

    }
}
