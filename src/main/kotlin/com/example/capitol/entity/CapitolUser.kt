package com.example.capitol.entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Table(name="capitoluser")
@Entity (name = "CapitolUser")
data class CapitolUser (
    @Id
    @Column(updatable = false, name="userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //it needs to be -1 then it will be replaced automatically by the generated value strategy
    var userId:Int=-1,

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL])
    private var capitolImages : Set<CapitolImage>,

    @Column(nullable=false, unique = true)
    var username:String,

    @Column(nullable=false)
    var password:String,

    @Column(nullable=false)
    var updated:LocalDateTime = LocalDateTime.now(),

    @Column(nullable=false)
    var created:LocalDateTime= LocalDateTime.now(),

    @Column
    var role:String="USER",

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
