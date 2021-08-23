package com.example.capitol.controller

import com.example.capitol.mail.ContactSender
import com.example.capitol.viewmodel.ContactViewModel
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.xml.bind.ValidationException

@RestController
@RequestMapping("/api")
@CrossOrigin
class ContactController (
    var contactSender:ContactSender
        ){


    @PostMapping("/contact")
            /**
             * @throws ResponseStatusException error 422 if email not valid email
             */
    fun sendContact(@RequestBody contactViewModel: ContactViewModel): Boolean{

       //massive nonsense regex that represents email
       if (!contactViewModel.email.matches("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])".toRegex())){
            throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                "Input is not an email")
       }

       /*
       xif (bindingResult.hasErrors()){
            return "Binding result has errors"}
            */
        this.contactSender.sendContact(
            contactViewModel.email,
            contactViewModel.name,
            contactViewModel.message
        )
       return true;
        //this.contactSender.sendContact("test@email.com", "name","message")
    }
}
