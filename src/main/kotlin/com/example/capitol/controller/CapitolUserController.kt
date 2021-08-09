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
        //check passwords first cause it's cheaper
        if ( newUserViewModel.password.compareTo(newUserViewModel.matchingPassword) != 0 )
            return "pwmismatch"

        //checks to see if password is 8-20 char
        if (!newUserViewModel.password.matches("^{8,20}\$".toRegex()))
            return "invalidPassword"

        //massive nonsense regex that represents email
        if (!newUserViewModel.username.matches("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])".toRegex()))
            return "invalidUsernameContents"

        //check to see if username exists
        //put this after the password/regex stuff because it requires an SQL call
        if ( capitolUserDetailsService.existsByUsername(newUserViewModel.username))
            return "exists"

        var newCapitolUser:CapitolUser = CapitolUser(username = newUserViewModel.username,
            password = newUserViewModel.password)
        capitolUserDetailsService.save(newCapitolUser)

        return "saved"
    }

    // @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    //@CrossOrigin
    @GetMapping("/user/exists/{username}")
    fun exists(@PathVariable username: String):Boolean{
        return !capitolUserDetailsService.existsByUsername(username);
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
    //@CrossOrigin
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
