package com.example.capitol.controller

import com.example.capitol.entity.Contact
import com.example.capitol.mail.ContactSender
import com.example.capitol.mail.EmailCfg
import com.example.capitol.viewmodel.ContactViewModel
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.xml.bind.ValidationException

@RestController
@RequestMapping("/api")
@CrossOrigin
class ContactController (
    var contactSender:ContactSender
        ){

   // @PreAuthorize("permitAll")
    @GetMapping("/contact")
    fun sendContact(@RequestBody contactViewModel: ContactViewModel,
                    bindingResult:BindingResult) {
        if (bindingResult.hasErrors())
            throw ValidationException("Contact info not valid")
        this.contactSender.sendContact(
            contactViewModel.email,
            contactViewModel.name,
            contactViewModel.message
        )


        //this.contactSender.sendContact("test@email.com", "name","message")
    }
}
