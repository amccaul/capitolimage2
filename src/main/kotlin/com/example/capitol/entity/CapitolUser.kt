package com.example.capitol.entity

import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Table(name="capitoluser")
@Entity (name = "CapitolUser")
//data class CapitolUser : User (
//data class CapitolUser (
data class CapitolUser (
    @Id
    @Column(name="userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId:Int = -1,

    @Column(nullable=false, unique = true)
    var username:String,

    @Column(nullable=false)
    var password:String,

    @Column(nullable=false)
    var updated:LocalDateTime = LocalDateTime.now(),

    @Column(nullable=false)
    var created:LocalDateTime= LocalDateTime.now(),

    @Column
    var role:String="",

    @Column
    var enabled:Boolean = true,

    @Column
    var expired:Boolean = false,

    @Column
    var locked:Boolean = false,

    @Column
    var credentialsLocked:Boolean = false,

    @Column
    var permissions:String = "",

){

    fun getRoleList(): List<String> {
        return if (role.length > 0) {
            Arrays.asList(*role.split(",").toTypedArray())
        } else ArrayList()
    }

    fun getPermissionList(): List<String> {
        return if (permissions.length > 0) {
            Arrays.asList(*permissions.split(",").toTypedArray())
        } else ArrayList()
    }
}