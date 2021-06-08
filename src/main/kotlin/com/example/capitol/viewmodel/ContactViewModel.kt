package com.example.capitol.viewmodel

import org.intellij.lang.annotations.Pattern
import org.jetbrains.annotations.NotNull
import javax.validation.Constraint


data class ContactViewModel (
    //TODO make these field values work
    //They're copied from Java but don't really work in Kotlin

    @field:NotNull
    var name:String,
    @field:NotNull
    @field:Pattern("^(.+)@(.+)\$")
    var email:String,
    @NotNull
    @field:Pattern("^.{10,}$")
    var message:String
    ){

}
