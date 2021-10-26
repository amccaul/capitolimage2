package com.example.capitol.controller

import com.example.capitol.config.CapitolUserDetailsService
import com.example.capitol.entity.CapitolUser
import com.example.capitol.viewmodel.LoginViewModel
import com.example.capitol.viewmodel.NewUserViewModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api")
@CrossOrigin
class CapitolUserController (
    @Autowired var capitolUserDetailsService: CapitolUserDetailsService,
    @Autowired var passwordEncoder: PasswordEncoder,
    @Autowired var authenticationManager: AuthenticationManager
    ){
    @PutMapping("/user/save")
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody newUserViewModel: NewUserViewModel):String{
        //check passwords first cause it's cheaper
        if ( newUserViewModel.password.compareTo(newUserViewModel.matchingPassword) != 0 )
            throw ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Password mismatch")

        //checks to see if password is 8-20 char
        //TODO get this regex working
        /*if (newUserViewModel.password.matches("^{8,20}\$".toRegex()))
            throw ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Invalid password"
            )
        */
        //massive nonsense regex that represents email
        //if (!newUserViewModel.email.matches("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])".toRegex())) {

        if (!newUserViewModel.email.matches("^[A-Za-z0-9+_.-]+@(.+)\$".toRegex())) {
            throw ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Input is not an email"
            )
        }
        //check to see if username exists
        //put this after the password/regex stuff because it requires an SQL call
        //TODO figure out why this doesn't check emails after program start
        if ( capitolUserDetailsService.existsByUsername(newUserViewModel.email))
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Username already exists")

        var newCapitolUser:CapitolUser = CapitolUser(username = newUserViewModel.email,
            password = passwordEncoder.encode(newUserViewModel.password))
        capitolUserDetailsService.save(newCapitolUser)

        return "User account created";
    }

    @GetMapping("/user/exists/{username}")
    fun exists(@PathVariable username: String):Boolean{
        return !capitolUserDetailsService.existsByUsername(username);
    }

    /**
     * @param Grabs username and password off basic auth header
     * @return true if authenticated, false if not
     */
    @GetMapping("/user/authenticate")
    fun authenticate(webRequest:NativeWebRequest):Boolean {
        val creds = webRequest.getHeader(HttpHeaders.AUTHORIZATION)!!.substring("Basic".length).trim();

        val decodedCreds = String(Base64.getDecoder().decode(creds)).split(":")
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(decodedCreds[0], decodedCreds[1])
        )
        return authentication.isAuthenticated
    }


    @GetMapping("/user/getAllUsers")
    fun getCapitolUser():List<CapitolUser>{
        return capitolUserDetailsService.findAll()
    }

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
    @GetMapping("/user/account")
    fun account(webRequest:NativeWebRequest): String {
        return "Hello " + webRequest.getHeader(HttpHeaders.AUTHORIZATION)
    }

    @GetMapping("/user/{username}")
    fun getCapitolUser(@PathVariable username: String): CapitolUser? {
        return capitolUserDetailsService.getCapitolUser(username)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "user:" + username+" does not exist")

    }

    /*

    fun deleteByUserID(@PathVariable userId :Int):String{
        capitolUserRepository.delete(userId)
        return "deleted"
    }
    */



}
