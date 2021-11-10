package com.example.capitol.controller

import com.example.capitol.config.CapitolUserDetailsService
import com.example.capitol.entity.CapitolUser
import com.example.capitol.viewmodel.NewUserViewModel
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.*
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post


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
    var NEWUSER_EXISTS = NewUserViewModel(email = "exists@address.com", password = "8characters", matchingPassword = "8characters")
    var NEWUSER_NOTEMAIL = NewUserViewModel(email = "potato", password = "8characters", matchingPassword = "8characters")
    var NEWUSER_MISMATCH= NewUserViewModel(email = "potato", password = "7characters", matchingPassword = "8characters")



    var PUBLIC = "/api/public/"
    var PUBLIC_EXISTS = PUBLIC+"exists/"
    var PUBLIC_SAVE = PUBLIC+"save/"

    @BeforeEach
    fun setup() {
        `when`(capitolUserDetailsService.save(this.CU)).thenReturn(this.CU)
        `when`(capitolUserDetailsService.existsByUsername("u")).thenReturn(true)
        `when`(capitolUserDetailsService.existsByUsername("u2")).thenReturn(false)
        `when`(capitolUserDetailsService.existsByUsername("exists@address.com")).thenReturn(true)


    }
    @Test
    fun `Test save passwords don't match, dawg`(){
        mockMvc.post(PUBLIC_SAVE){
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(NEWUSER_EXISTS)
        }.andExpect {
            status { HttpStatus.BAD_REQUEST }
        }
    }
    @Test
    fun `Test save username ain't email, dawg`(){
        mockMvc.post(PUBLIC_SAVE){
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(NEWUSER_EXISTS)
        }.andExpect {
            status { HttpStatus.UNPROCESSABLE_ENTITY }
        }
    }

    @Test
    fun `Test save username already exist yo`(){
        mockMvc.post(PUBLIC_SAVE){
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(NEWUSER_EXISTS)
        }.andExpect {
            status { HttpStatus.CONFLICT }
        }
    }

    @Test
    fun `Test save successful`(){
        mockMvc.post(PUBLIC_SAVE){
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(NEWUSER_GOOD)
        }.andExpect {
            status { HttpStatus.OK }
            content { "User account created" }
        }
    }

    @Test
    fun `user exists`(){
        mockMvc.get(PUBLIC_EXISTS+"u"){}.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON ) }
            content { string("true")}
        }
    }
    @Test
    fun `user no exists`(){
        mockMvc.get(this.PUBLIC_EXISTS+"u2"){}.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON ) }
            content { string("false")}
        }
    }

}