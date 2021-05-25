package com.example.capitol.viewmodel

import org.intellij.lang.annotations.Pattern
import org.jetbrains.annotations.NotNull

data class ContactViewModel (
    //TODO make these field values work
    @field:NotNull
    @field:Pattern("^(.+)@(.+)\$")
    var email:String,
    @field:NotNull
    var name:String,
    @field:NotNull
    @field:Pattern("^.{10,}$")
    var message:String
    ){

}
