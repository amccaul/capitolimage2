package com.example.capitol.controller

import com.example.capitol.config.CapitolUserDetailsService
import com.example.capitol.viewmodel.NewUserViewModel
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest
internal class CapitolUserControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc:MockMvc

    @MockBean
    lateinit var capitolUserDetailsService: CapitolUserDetailsService

}
