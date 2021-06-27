package com.example.capitol.repository

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
class DbInit(var capitolUserRepository: CapitolUserRepository):CommandLineRunner {
    @Autowired
    lateinit var passwordEncoder:PasswordEncoder

    override fun run(vararg args: String?) {
        capitolUserRepository.deleteAll()

        var password = passwordEncoder.encode("pass123")
        var u:CapitolUser = CapitolUser(username="u", password = password)
                //passwordEncoder.encode("{noop}pass123"))
                u.role="USER"
        capitolUserRepository.save(u)

        var a:CapitolUser = CapitolUser(username="admin",
            password = password)
            //passwordEncoder.encode("{noop}pass123"))
        a.role="ADMIN"
        capitolUserRepository.save(a)

    }
}
