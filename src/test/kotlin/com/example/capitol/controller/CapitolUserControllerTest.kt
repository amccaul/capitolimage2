package com.example.capitol.controller

import com.example.capitol.entity.CapitolUser
import com.example.capitol.repository.CapitolUserRepository
import com.example.capitol.viewmodel.NewUserViewModel
import com.fasterxml.jackson.databind.ObjectMapper
import org.aspectj.lang.annotation.Before
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

internal class CapitolUserControllerTest {

    lateinit var mvc:MockMvc

    @Mock
    private lateinit var capitolUserRepository:CapitolUserRepository

    @InjectMocks
    private lateinit var capitolUserController:CapitolUserController

    private lateinit var jsonCapitolUser:JacksonTester<CapitolUser>

    @BeforeEach
    fun setup(){
        JacksonTester.initFields(this, ObjectMapper())
        mvc = MockMvcBuilders.standaloneSetup(capitolUserController)
           // .setControllerAdvice(SuperHeroExceptionHandler())
           // .addFilters(new SuperHeroFilter())
            .build();

    }

    @Test
    fun contextLoads(){
        Assertions.assertThat(capitolUserController).isNotNull();
    }
    @Test
    fun save(){

        var newUserViewModel:NewUserViewModel = NewUserViewModel()
        var capitolUser = CapitolUser(username = "Testusername", password = "insecurePassword")
        capitolUserController.save(capitolUser)
        assertTrue(capitolUserController.exists(capitolUser.username))


    }


}
