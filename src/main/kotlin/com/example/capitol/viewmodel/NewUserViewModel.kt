package com.example.capitol.viewmodel

import com.sun.istack.NotNull
import javax.validation.constraints.Pattern

//don't touch these elements!  It defines the API!
data class NewUserViewModel (
    var username:String,
    var password:String,
    var matchingPassword:String
){}
