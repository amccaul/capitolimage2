package com.example.capitol.controller

import com.example.capitol.config.CapitolUserDetailsService
import com.example.capitol.entity.CapitolUser
import com.example.capitol.viewmodel.NewUserViewModel
import org.assertj.core.util.Arrays
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.*
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.Base64Utils
import org.springframework.web.context.support.WebApplicationContextUtils
import javax.servlet.ServletContext


@AutoConfigureMockMvc // auto-magically configures and enables an instance of MockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Why configure Mockito manually when a JUnit 5 test extension already exists for that very purpose?
@ExtendWith(SpringExtension::class, MockitoExtension::class)
class CapitolUserControllerUnitTest_userfunctions {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var capitolUserDetailsService: CapitolUserDetailsService

    var CU = CapitolUser(username = "u", password = "pass123")

    var NEWUSER_GOOD = NewUserViewModel(email = "email@address.com", password = "8characters", matchingPassword = "8characters")
    var NEWUSER_EXISTS = NewUserViewModel(email = "exists@address.com", password = "8characters", matchingPassword = "8characters")
    var NEWUSER_NOTEMAIL = NewUserViewModel(email = "potato", password = "8characters", matchingPassword = "8characters")
    var NEWUSER_MISMATCH= NewUserViewModel(email = "potato", password = "7characters", matchingPassword = "8characters")


    var USER = "/api/user/"
    var AUTHENTICATE = USER+"authenticate/"
    var DELETE = USER + "delete/"


    val USER_ARRAY = Arrays.array("USER")

    @BeforeEach
    fun setup() {
        `when`(capitolUserDetailsService.save(this.CU)).thenReturn(this.CU)
        `when`(capitolUserDetailsService.existsByUsername("u")).thenReturn(true)
        `when`(capitolUserDetailsService.existsByUsername("u2")).thenReturn(false)
        `when`(capitolUserDetailsService.existsByUsername("exists@address.com")).thenReturn(true)

        //this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
    /*
    *  Test user/authenticate
    *
    *
     */
    @Test
    //@PreAuthorize("hasRole('USER')")
    @WithMockUser(username = "john", password = "Cena", roles = arrayOf("USER"))
    //@WithMockUser(username = "u", password = "pass123")
    fun `test-authenticate successfully authenticated`  (){
        val h = HttpHeaders()
        h.add(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("u:pass123".toByteArray()))
        this.mockMvc.perform(get(AUTHENTICATE)
            .with(csrf())
            .contentType(APPLICATION_JSON)
            .headers(h)
            .accept(APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful);


    }

    @Test
    @WithMockUser(username = "u", password = "pass123")
    fun `test-authenticate unsuccessfully authenticated`  (){


    /*mockMvc.get(AUTHENTICATE){
            header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("u shall not pass:pass123".toByteArray()))
        }.andExpect {
            HttpStatus.OK;
        }*/
    }


    @Test
    fun `test-authenticate error 401 no user`  (){

    /*mockMvc.get(AUTHENTICATE)
            .andExpect {
                HttpStatus.UNAUTHORIZED }*/

    }


}