package com.example.capitol.repository

import com.example.capitol.viewmodel.NewUserViewModel
import com.example.capitol.entity.CapitolUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CapitolUserRepository : JpaRepository<CapitolUser, Int> {

    @Query("SELECT u FROM CapitolUser u WHERE u.username = " +
            ":username")
    fun findByUsername(@Param("username") username:String):CapitolUser?
    /*
    @Query("select case when count(c)> 0 then true else false end from Car c where lower(c.model) like lower(:model)")
    boolean existsCarLikeCustomQuery(@Param("model") String model);
    */

    @Query ("SELECT case WHEN COUNT(u)>0 THEN true ELSE false END " +
            "from CapitolUser u WHERE lower(u.username) LIKE :usernameinput")
    fun existsByUsername(@Param("usernameinput") username:String):Boolean

    /*

    @Query(value = "SELECT * FROM CapitolUser", nativeQuery= true)
    override fun findAll(): MutableList<CapitolUser>
    */





    /*
    @Query("SELECT u FROM CapitolUser u WHERE u.email = " +
            ":email")
    fun findByEmail(email:String):CapitolUser

    @Query("DELETE FROM CapitolUser u WHERE u.userId = " +
            ":userId")
    fun deleteByUserID(userId:Int):CapitolUser
    `*/

}
