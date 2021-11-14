package com.example.capitol.controller

import com.example.capitol.config.CapitolUserDetailsService
import com.example.capitol.entity.CapitolUser
import com.example.capitol.viewmodel.NewUserViewModel
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc // auto-magically configures and enables an instance of MockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Why configure Mockito manually when a JUnit 5 test extension already exists for that very purpose?
@ExtendWith(SpringExtension::class, MockitoExtension::class)
class CapitolUserControllerUnitTest_publicfunctions {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var capitolUserDetailsService: CapitolUserDetailsService

    var CU = CapitolUser(username = "u", password = "pass123")

    var NEWUSER_GOOD = NewUserViewModel(email = "email@address.com", password = "8characters", matchingPassword = "8characters")
    var CU_GOOD = CapitolUser(username = NEWUSER_GOOD.email, password = NEWUSER_GOOD.password)

    var NEWUSER_EXISTS = NewUserViewModel(email = "exists@address.com", password = "8characters", matchingPassword = "8characters")
    var CU_EXISTS = CapitolUser(username = NEWUSER_EXISTS.email, password = NEWUSER_EXISTS.password)
    var NEWUSER_NOTEMAIL = NewUserViewModel(email = "potato", password = "8characters", matchingPassword = "8characters")
    var NEWUSER_MISMATCH= NewUserViewModel(email = "email@address.com", password = "7characters", matchingPassword = "8characters")

    var PUBLIC = "/api/public/"
    var PUBLIC_EXISTS = PUBLIC+"exists/"
    var PUBLIC_SAVE = PUBLIC+"save/"

    val GSON = Gson()


    @BeforeEach
    fun setup() {
        `when`(capitolUserDetailsService.save(this.CU)).thenReturn(this.CU)

        `when`(capitolUserDetailsService.save(this.CU_GOOD)).thenReturn(this.CU_GOOD)

        `when`(capitolUserDetailsService.existsByUsername("u")).thenReturn(true)
        `when`(capitolUserDetailsService.existsByUsername("u2")).thenReturn(false)
        `when`(capitolUserDetailsService.existsByUsername("exists@address.com")).thenReturn(true)

        //this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    /*
     *  Test /public/save
     */
    @Test
    fun `Test save passwords don't match, dawg`(){
        mockMvc.perform(post(PUBLIC_SAVE)
            .content(GSON.toJson(NEWUSER_MISMATCH))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(status().reason("Password mismatch"))
    }
    @Test
    fun `Test save username ain't email, dawg`(){
        mockMvc.perform(post(PUBLIC_SAVE)
            .content(GSON.toJson(NEWUSER_NOTEMAIL))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(status().reason("Input is not an email"))
    }
    @Test
    fun `Test-save successful`(){
        mockMvc.perform(post(PUBLIC_SAVE)
            .content ( GSON.toJson(NEWUSER_GOOD))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful)

    }

    @Test
    fun `Test-save username already exist yo`(){
        mockMvc.perform(post(PUBLIC_SAVE)
            .content(GSON.toJson(NEWUSER_EXISTS))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict())
            .andExpect(status().reason("Username already exists"))
    }

    /*
    * Test user exists
     */
    @Test
    fun `user exists`(){
        mockMvc.perform(get(PUBLIC_EXISTS+"u"))
            .andExpect(status().isOk)
            .andExpect(content().string("true"))
    }
    @Test
    fun `user no exists`(){
        mockMvc.perform(get(PUBLIC_EXISTS+"u2"))
            .andExpect(status().isOk)
            .andExpect(content().string("false"))
    }

}