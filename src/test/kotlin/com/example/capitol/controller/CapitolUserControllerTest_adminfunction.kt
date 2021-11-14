package com.example.capitol.controller

import com.example.capitol.config.CapitolUserDetailsService
import com.example.capitol.entity.CapitolUser
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
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete


@AutoConfigureMockMvc // auto-magically configures and enables an instance of MockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Why configure Mockito manually when a JUnit 5 test extension already exists for that very purpose?
@ExtendWith(SpringExtension::class, MockitoExtension::class)
class CapitolUserControllerUnitTest_adminfunction {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var capitolUserDetailsService: CapitolUserDetailsService

    var ADMIN_URL = "/api/admin/"
    var GETALL_URL = ADMIN_URL + "getall/"
    var DELETE_URL = ADMIN_URL + "delete/{userId}"


    var CU = CapitolUser(username = "u", password = "pass123")
    var CU_TWO = CapitolUser(username = "Gary Powers", password = "Russia")

    @BeforeEach
    fun setup() {
        `when`(capitolUserDetailsService.delete(3)).thenReturn(true)
        `when`(capitolUserDetailsService.delete(4)).thenReturn(false)

        var listofCU = listOf<CapitolUser>(CU, CU_TWO)
        `when`(capitolUserDetailsService.findAll()).thenReturn(listofCU)
        //this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    //TODO figure out why this test is passing with 34
    @Test
    @WithMockUser(username = "u", password = "pass123", roles = ["ADMIN"])
    fun `delete successful`(){
        mockMvc.delete(DELETE_URL, 34)
            .andExpect {
                HttpStatus.OK
            }
    }


    //TODO figure out why this test is passing with 45
    @Test
    @WithMockUser(username = "u", password = "pass123", roles = ["ADMIN"])
    fun `delete user not found`(){

    }
    @Test
    @WithMockUser(username = "u", password = "pass123", roles = ["USER"])
    fun `delete user (fail)`(){

    }
    @Test
    fun `delete no auth (fail)`(){

    }

    @Test
    @WithMockUser(username = "u", password = "pass123", roles = ["ADMIN"])
    fun `get all successful`(){
        mockMvc.get(GETALL_URL)
            .andExpect {
                HttpStatus.OK
            }
    }
    @Test
    @WithMockUser(username = "u", password = "pass123", roles = ["USER"])
    fun `get all as user (fail)`(){
        mockMvc.get(GETALL_URL)
            .andExpect {
                HttpStatus.UNAUTHORIZED;
            }
    }
    @Test
    fun `get all no auth (fail)`(){
        mockMvc.get(GETALL_URL)
            .andExpect {
            HttpStatus.UNAUTHORIZED;
        }
    }


}