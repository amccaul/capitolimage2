package com.example.capitol.service

import com.example.capitol.entity.CapitolUser
import com.example.capitol.viewmodel.LoginViewModel
import com.example.capitol.viewmodel.NewUserViewModel

interface CapitolUserService {
    fun save(newUserViewModel: NewUserViewModel): CapitolUser
    fun checkUsername( username:String ): Boolean
    fun authenticate(user: LoginViewModel):CapitolUser
}
