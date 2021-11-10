package com.example.capitol.repository

import com.example.capitol.viewmodel.NewUserViewModel
import com.example.capitol.entity.CapitolUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CapitolUserRepository : JpaRepository<CapitolUser, Int> {

    @Query ("SELECT u from CapitolUser u WHERE lower(u.username) LIKE :usernameinput")
    fun findByUsername(@Param("usernameinput") username:String):CapitolUser?

    @Query ("SELECT u from CapitolUser u WHERE u.userId = :useridinput")
    fun findByUserId(@Param("useridinput") userIdInput: Int):CapitolUser?

    /*

    @Query("select case when count(c)> 0 then true else false end from Car c where lower(c.model) like lower(:model)")
    boolean existsCarLikeCustomQuery(@Param("model") String model);
    */

    @Query ("SELECT case WHEN COUNT(u)>0 THEN true ELSE false END " +
            "from CapitolUser u WHERE lower(u.username) LIKE :usernameinput")
    fun existsByUsername(@Param("usernameinput") username:String):Boolean


    @Query ("SELECT case WHEN COUNT(u)>0 THEN true ELSE false END from CapitolUser u " +
            "WHERE lower(u.username) LIKE :usernameinput AND u.password LIKE :passwordinput")
    fun authenticate(@Param("usernameinput") username:String, @Param("passwordinput") password:String):Boolean

    /*
    @Query(value = "SELECT * FROM CapitolUser", nativeQuery= true)
    override fun findAll(): MutableList<CapitolUser>
    */

    /*
    @Query("SELECT u FROM CapitolUser u WHERE u.email = " +
            ":email")
    fun findByEmail(email:String):CapitolUser
*/

    @Query("DELETE FROM CapitolUser u WHERE u.userId = " +
            ":userId")
    fun deleteByUserID(userId:Int): Integer

}
