package com.example.capitol.repository

import com.example.capitol.viewmodel.NewUserViewModel
import com.example.capitol.entity.CapitolUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CapitolUserRepository : JpaRepository<CapitolUser, Int> {

    @Query("SELECT CASE WHEN count(u)>0 " +
            "THEN true ELSE false END FROM CapitolUser u WHERE u.username =:username")
    fun exists(username:String):Boolean

    @Query("SELECT u FROM CapitolUser u WHERE u.username = " +
            ":username")
    fun findByUsername(username:String):CapitolUser

    @Query("SELECT u FROM CapitolUser u")
    override fun findAll(): MutableList<CapitolUser>





    /*
    @Query("SELECT u FROM CapitolUser u WHERE u.email = " +
            ":email")
    fun findByEmail(email:String):CapitolUser

    @Query("DELETE FROM CapitolUser u WHERE u.userId = " +
            ":userId")
    fun deleteByUserID(userId:Int):CapitolUser
    `*/

}
