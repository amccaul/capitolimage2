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
class CapitolUserController (
    @Autowired var capitolUserDetailsService: CapitolUserDetailsService
    ){

    /*
    @Autowired
    lateinit var capitolUserRepository: CapitolUserRepository
    */



    @PutMapping("/user/save")
    fun save(@RequestBody newUserViewModel: NewUserViewModel):String{
        //check to see if passwords don't match
        if ( newUserViewModel.password != newUserViewModel.matchingPassword )
            return "pwmismatch"

        //check to see if password is invalid

        //TODO check to see if username invalid
        //figure out how to do email regex using java constants

        //check to see if username exists
        //put this after the password/regex stuff because it requires an SQL call
        if ( capitolUserDetailsService.existsByUsername(newUserViewModel.username))
            return "exists"

        var newCapitolUser:CapitolUser = CapitolUser(username = newUserViewModel.username,
            password = newUserViewModel.password)
        capitolUserDetailsService.save(newCapitolUser)

        return "saved"
    }

    @GetMapping("/user/exists/")
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
    /*
    @GetMapping("/user/getAllUsers")
    fun getCapitolUser():List<CapitolUser>{
        return capitolUserDetailsService.findAll()
    }
    */
   /* TODO Get working
    @GetMapping("/user/getAllUsers")
    fun getCapitolUser():String{
        var list = capitolUserDetailsService.findAll()
        var output:String = ""
        for ( l in list){
            output += l.toString()
        }
        return output;
    }
*/

    //Maybe should be post?  Idk.
    @GetMapping("/user/account")
    fun account(): String {
        return "account-details works!"
    }

    @GetMapping("/user/{username}")
    fun getCapitolUser(@PathVariable username: String): CapitolUser? {
        return capitolUserDetailsService.getCapitolUser(username)
    }

    /*

    fun deleteByUserID(@PathVariable userId :Int):String{
        capitolUserRepository.delete(userId)
        return "deleted"
    }
    */



}
