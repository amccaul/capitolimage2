package com.example.capitol.mail

interface ContactSender {
   fun sendContact(from:String, name:String, feedback:String):Unit

}
