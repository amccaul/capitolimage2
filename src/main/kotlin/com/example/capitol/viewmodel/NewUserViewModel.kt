package com.example.capitol.viewmodel

import com.sun.istack.NotNull
import javax.validation.constraints.Pattern

//don't touch these elements!  It defines the API!
data class NewUserViewModel (

    @field:NotNull
    //TODO get email validator working
    //@Pattern(regexp = Constraints.EMAIL_REGEX)
    var username:String,

    @field:NotNull
    //any character between 8 and 20 characters is valid
    @Pattern(regexp = "^.{8,20}\$")
    var password:String,

    @field:NotNull
    @Pattern(regexp = "^.{8,20}\$")
    var matchingPassword:String
){}
