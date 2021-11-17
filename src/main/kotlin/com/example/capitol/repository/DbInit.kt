package com.example.capitol.repository

import com.example.capitol.entity.CapitolImage
import com.example.capitol.entity.CapitolUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
//TODO delete this in production
//@ComponentScan("com.example.capitol.config")
class DbInit(var capitolUserRepository: CapitolUserRepository,
             var capitolImageRepository: CapitolImageRepository,
             @Autowired var passwordEncoder:PasswordEncoder
             ):CommandLineRunner {
    override fun run(vararg args: String?) {
        capitolImageRepository.deleteAll()
        capitolUserRepository.deleteAll()

        var password = passwordEncoder.encode("pass123")
        var u = CapitolUser(username="u", password = password)
                //passwordEncoder.encode("{noop}pass123"))
                u.role="USER"
        capitolUserRepository.save(u)

        var a = CapitolUser(username="admin@123.com",
            password = password)
            //passwordEncoder.encode("{noop}pass123"))
        a.role="ADMIN"
        capitolUserRepository.save(a)
        var u2 = capitolUserRepository.findByUsername("u") as CapitolUser
        var i1 = CapitolImage(image_Id = 3, capitolUser = u2, image_name = "Chad.jpg", url = "C:\\Users\\Alec\\Pictures\\output\\Chad.jpg");
        capitolImageRepository.save(i1)
    }
}
