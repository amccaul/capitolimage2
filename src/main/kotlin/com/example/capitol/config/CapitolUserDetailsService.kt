package com.example.capitol.config

import com.example.capitol.entity.CapitolUser
import com.example.capitol.repository.CapitolUserRepository
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CapitolUserDetailsService (_capitolUserRepository:CapitolUserRepository): UserDetailsService {
    var capitolUserRepository: CapitolUserRepository = _capitolUserRepository

    override fun loadUserByUsername(username: String): UserDetails {
        var capitolUser: CapitolUser = capitolUserRepository.findByUsername(username)
        return CapitolUserDetails(capitolUser)
    }

    fun exists(username:String): Boolean {
        var capitolUser: CapitolUser = capitolUserRepository.findByUsername(username)
        return capitolUser != null
    }

}
