package com.example.capitol.controller

import com.example.capitol.entity.CapitolUser
import com.example.capitol.repository.CapitolUserRepository
import com.example.capitol.service.CapitolUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
@CrossOrigin
class CapitolUserController {

    @Autowired
    lateinit var capitolUserRepository: CapitolUserRepository

    @Autowired
    lateinit var capitolUserService: CapitolUserService

    @PostMapping("/user/save")
    fun save(@RequestBody capitolUser: CapitolUser):String{
        capitolUserRepository.save(capitolUser)
        return "saved"
    }

    @PostMapping("/user/exists")
    fun exists(@RequestBody username: String):Boolean{
        return capitolUserRepository.exists(username)

    }

    @PostMapping("/login")
    fun login(@RequestBody username: String, password:String):Boolean{
        //return capitolUserRepository.exists(username)
        //capitolUserService.
        return true
    }


    @GetMapping("/user/getAllUsers")
    fun getCapitolUser():List<CapitolUser>{
        return capitolUserRepository.findAll()
    }



    @GetMapping("/account")
    fun account(): String {
        return "account-details works!"
    }

    @GetMapping("/{username}")
    fun getCapitolUser(@PathVariable username: String): CapitolUser {
        return capitolUserRepository.findByUsername(username)
    }

    /*

    fun deleteByUserID(@PathVariable userId :Int):String{
        capitolUserRepository.delete(userId)
        return "deleted"
    }
    */



}
