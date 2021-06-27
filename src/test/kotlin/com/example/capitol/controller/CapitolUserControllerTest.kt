package com.example.capitol.controller

import com.example.capitol.config.CapitolUserDetailsService
import com.example.capitol.entity.CapitolUser
import com.example.capitol.repository.CapitolUserRepository
import com.example.capitol.viewmodel.NewUserViewModel
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

internal class CapitolUserControllerUnitTest {

    @Test
    fun shouldSave() {
        var newUserViewModel = NewUserViewModel(
            username = "testUsername",
            password = "password",
            matchingPassword = "password"
        )
        var capitolUserDetailsService = Mockito.mock(CapitolUserDetailsService::class.java)
        var capitolUserController = CapitolUserController(capitolUserDetailsService)
        assertEquals("saved", capitolUserController.save(newUserViewModel))
    }

    /*
    @Test
    fun alreadyExists(){

        var newUserViewModel = NewUserViewModel(username="testUsername",
            password = "password",
            matchingPassword = "password")
        var capitolUserDetailsService = Mockito.mock(CapitolUserDetailsService::class.java)

        //TODO figure out mockito when clause in Kotlin
        Mockito.`when` (capitolUserDetailsService.getCapitolUser(username = "testUsername").thenReturn(true))

        var capitolUserController = CapitolUserController(capitolUserDetailsService)
        assertEquals("exists", capitolUserController.save(newUserViewModel) )
    }
*/
    @Test
    fun passwordMismatch(){
        var newUserViewModel = NewUserViewModel(username="testUsername",
            password = "password",
            matchingPassword = "differentpassword")
        var capitolUserDetailsService = Mockito.mock(CapitolUserDetailsService::class.java)
        var capitolUserController = CapitolUserController(capitolUserDetailsService)
        assertEquals("pwmismatch", capitolUserController.save(newUserViewModel))
    }



}
