package com.example.capitol.config

import com.example.capitol.entity.CapitolUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.ArrayList

class CapitolUserDetails : UserDetails {
    var capitolUser : CapitolUser
    constructor(_capitolUser: CapitolUser){
        capitolUser = _capitolUser
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableList<GrantedAuthority> = ArrayList()

        // Extract list of permissions (name)
        this.capitolUser.getPermissionList().forEach { p ->
            val authority: GrantedAuthority = SimpleGrantedAuthority(p)
            authorities.add(authority)
        }

        // Extract list of roles (ROLE_name)
        this.capitolUser.getRoleList().forEach { r ->
            val authority: GrantedAuthority = SimpleGrantedAuthority("ROLE_$r")
            authorities.add(authority)
        }

        return authorities
    }

    override fun getPassword(): String {
        return capitolUser.password
    }

    override fun getUsername(): String {
        return capitolUser.username
    }

    override fun isAccountNonExpired(): Boolean {
        return !capitolUser.expired
    }

    override fun isAccountNonLocked(): Boolean {
        return !capitolUser.locked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return !capitolUser.credentialsLocked
    }

    override fun isEnabled(): Boolean {
        return capitolUser.enabled
    }


}
