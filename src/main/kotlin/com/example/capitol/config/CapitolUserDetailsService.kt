package com.example.capitol.config

import com.example.capitol.entity.CapitolUser
import com.example.capitol.repository.CapitolUserRepository
import net.bytebuddy.implementation.bytecode.assign.TypeCasting
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CapitolUserDetailsService (_capitolUserRepository:CapitolUserRepository): UserDetailsService {
    var capitolUserRepository: CapitolUserRepository = _capitolUserRepository


    //leave it alone, overrides method, use getCapitolUser(username) instead
    override fun loadUserByUsername(username: String): UserDetails? {
        return capitolUserRepository.findByUsername(username)?.let { CapitolUserDetails(it) }
    }


    /**
     * @Returns CapitolUser object, null if not exists
     */
    fun getCapitolUser(username:String):CapitolUser? {
        return capitolUserRepository.findByUsername( username )
    }
    /**
     * @Returns false if username not found, true if username found
     */
    fun existsByUsername(username:String):Boolean {
        return capitolUserRepository.existsByUsername(username)
    }


    /**
     * @Returns all capitolusers in database
     */
    //TODO figure out why this doesn't return anything, ever
    fun findAll():List<CapitolUser>{
        return capitolUserRepository.findAll()
    }

    private fun saveAll( capitolUser:List<CapitolUser>):List<CapitolUser>{
        return capitolUserRepository.saveAll(capitolUser)
    }

    fun save (capitolUser: CapitolUser):CapitolUser {
        var listCapitolUser:List<CapitolUser> = listOf(capitolUser)
        return this.saveAll(listCapitolUser).get(0)
    }







}
