package com.example.capitol.viewmodel

import com.sun.istack.NotNull


data class NewUserViewModel (

    @field:NotNull
    var username:String,

    @field:NotNull
    var password:String,

    @field:NotNull
    var matchingPassword:String
){}
