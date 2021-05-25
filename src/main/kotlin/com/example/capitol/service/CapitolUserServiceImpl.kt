package com.example.capitol.service

import com.example.capitol.entity.CapitolUser
import com.example.capitol.repository.CapitolUserRepository
import com.example.capitol.viewmodel.LoginViewModel
import com.example.capitol.viewmodel.NewUserViewModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

import org.springframework.stereotype.Service

@Service
class CapitolUserServiceImpl : CapitolUserService {

    @Autowired
    private lateinit var passwordEncoder : PasswordEncoder

    @Autowired
    private lateinit var capitolUserRepository: CapitolUserRepository

    override fun save(newUserViewModel: NewUserViewModel): CapitolUser {
       // return CapitolUser()
        var password:String = passwordEncoder.encode(newUserViewModel.password)
        var capitolUser: CapitolUser = CapitolUser(
            username = newUserViewModel.username,
            password = password)

        return capitolUserRepository.save(capitolUser)
    }


    override fun authenticate(user: LoginViewModel) : CapitolUser{
        var capitolUser = capitolUserRepository.findByUsername(user.email)
        //return passwordEncoder.encode(user.password)==capitolUser.password;
        return capitolUser

    }

    override fun checkUsername(username:String ): Boolean {
        return capitolUserRepository.exists(username)

    }
/*

    override fun checkLogin(user: LoginViewModel):AuthenticationBean{
        val pwHash:String = passwordEncoder.encode(user.password)
        return capitolUserRepository.checkLogin(user.email, pwHash)
    }

*/

}
