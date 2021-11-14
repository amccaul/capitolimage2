package com.example.capitol.controller

import com.example.capitol.config.CapitolUserDetailsService
import com.example.capitol.entity.CapitolUser
import com.example.capitol.viewmodel.LoginViewModel
import com.example.capitol.viewmodel.NewUserViewModel
import com.example.capitol.viewmodel.UserModel
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
@CrossOrigin(origins = ["http://localhost:4200"], allowedHeaders = ["*"])
//@CrossOrigin(origins = ["*"])
class CapitolUserController (
    @Autowired var capitolUserDetailsService: CapitolUserDetailsService,
    @Autowired var passwordEncoder: PasswordEncoder,
    @Autowired var authenticationManager: AuthenticationManager
    ){
    @PostMapping("/public/save")
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody newUserViewModel: NewUserViewModel):String{
        //check passwords first cause it's cheaper
        if ( newUserViewModel.password.compareTo(newUserViewModel.matchingPassword) != 0 )
            throw ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Password mismatch")

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

    /**
     * @Param username string in path
     * @Return true if exists
     */
    @GetMapping("/public/exists/{username}")
    fun exists(@PathVariable username: String):Boolean{
        return capitolUserDetailsService.existsByUsername(username);
    }

    /**
     * @param Authorization header username and password
     * @return true if authenticated, false if not
     */
    // TODO add appropriate error codes, if any applicable
    @CrossOrigin(origins = ["http://localhost:4200"], allowedHeaders = ["*"])
    @GetMapping("/user/authenticate")
    fun authenticate(webRequest:NativeWebRequest): UserModel? {
        val creds = webRequest.getHeader(HttpHeaders.AUTHORIZATION)!!.substring("Basic".length).trim();

        val decodedCreds = String(Base64.getDecoder().decode(creds)).split(":")
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(decodedCreds[0], decodedCreds[1])
        )
        if(authentication.isAuthenticated){

            var cu = capitolUserDetailsService.getCapitolUser(decodedCreds[0])

            var user:UserModel = UserModel(
                cu!!.userId,
                cu.username,
                cu.role,
                cu.created,
                cu.updated,
                creds)
            return user;

        } else return null;

    }
    @GetMapping("/user/{username}")
    fun getCapitolUser(@PathVariable username: String): CapitolUser? {
        return capitolUserDetailsService.getCapitolUser(username)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "user: " + username+" does not exist")

    }
    @DeleteMapping("/user/delete/")
    fun deleteCapitolUser(@PathVariable userId: Int, webRequest: NativeWebRequest):Boolean{
        var user : CapitolUser? = capitolUserDetailsService.getCapitolUser(userId);

        if (user==null){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,
                "userId: " + userId+" does not exist") ;}
        //checks to see if user is authorised to delete that user
        var usermodel = authenticate(webRequest);
        if (usermodel != null){
            //only the user can delete their own account using this function
            if (usermodel.userId == userId){
                return capitolUserDetailsService.delete(usermodel.userId)
            }
        }
        return false;
    }

    @GetMapping("/admin/getAllUsers")
    fun getCapitolUser():List<CapitolUser>{
        return capitolUserDetailsService.findAll()
    }
    @DeleteMapping("/admin/delete/{userId}")
    fun deleteCapitolUser(@PathVariable userId : Int):Boolean{
        if ( !capitolUserDetailsService.existsByUserId(userId) )
            throw ResponseStatusException(HttpStatus.NOT_FOUND,
                "userId: " + userId+" does not exist")
        return capitolUserDetailsService.delete(userId)
    }




}
