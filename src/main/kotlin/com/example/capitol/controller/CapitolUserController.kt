package com.example.capitol.controller

import com.example.capitol.config.CapitolUserDetailsService
import com.example.capitol.entity.CapitolUser
import com.example.capitol.repository.CapitolUserRepository
import com.example.capitol.viewmodel.NewUserViewModel
import com.example.capitol.viewmodel.PasswordMatchesValidator
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
    lateinit var capitolUserDetailsService: CapitolUserDetailsService

    @PostMapping("/user/save")
    fun save(@RequestBody newUserViewModel: NewUserViewModel):String{
        //TODO check on server side to see if username exists and if it's valid
        //capitolUserRepository.exists(capitolUser.username)

        //check to see if passwords match

        //check to see if password is valid

        //check to see if username is valid

        //check to see if username exists



        //var newCapitolUser:CapitolUser =
        //capitolUserRepository.save(newCapitolUser)
        return "saved"
    }

    @PostMapping("/user/exists")
    fun exists(@RequestBody username: String):Boolean{
        return capitolUserDetailsService.existsByUsername(username);
    }
/*
    //TODO get working
    @PostMapping("/user/login")
    fun login(@RequestBody username: String, password:String):Boolean{
        return capitolUserRepository.exists(username)
        //capitolUserService.
        //return true
    }
*/

/*
    @GetMapping("/user/getAllUsers")
    fun getCapitolUser():String{
        return "Teststring"
    }
    */
    @GetMapping("/user/getAllUsers")
    fun getCapitolUser():List<CapitolUser>{
        return capitolUserDetailsService.findAll()
    }


    @GetMapping("/user/account")
    fun account(): String {
        return "account-details works!"
    }

    @GetMapping("/user/{username}")
    fun getCapitolUser(@PathVariable username: String): CapitolUser? {
        return capitolUserRepository.findByUsername(username)
    }

    /*

    fun deleteByUserID(@PathVariable userId :Int):String{
        capitolUserRepository.delete(userId)
        return "deleted"
    }
    */



}
