package com.example.capitol.config

import com.example.capitol.entity.CapitolUser
import com.example.capitol.repository.CapitolUserRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.internal.bytebuddy.dynamic.DynamicType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UserDetails

/**
 * Very little testing went into this because there's no logic in the service class
 */
@SpringBootTest
internal class CapitolUserDetailsServiceTest {

    var capitolUserRepository:CapitolUserRepository = Mockito.mock(CapitolUserRepository::class.java)

    var capitalUserDetailsService:CapitolUserDetailsService = CapitolUserDetailsService(capitolUserRepository);

    @BeforeEach
    internal fun setUp() {

    }

    @Test
    fun contextLoads(){
        assertThat(capitalUserDetailsService).isNotNull();
    }

    @Test
    fun testExists(){
        //var capitolUser = CapitolUser(userId = 100, username = "Potato@email.com", password = "hunter2")
        //Mockito.`when`(capitolUserRepository.findByUserId(100)).thenReturn(capitolUser)
        //Mockito.`when`(capitolUserRepository.findByUsername("Potato@email.com")).thenReturn(capitolUser)
        Mockito.`when`(capitolUserRepository.existsByUsername("Potato@email.com")).thenReturn(true)
        Mockito.`when`(capitolUserRepository.existsByUsername("Nonexistent username")).thenReturn(false)

        //ensure nonexistent username is not found and returns false
        assertFalse(capitalUserDetailsService.existsByUsername("Nonexistent username"));
        //ensure existent username is found and returns true
        assertTrue(capitalUserDetailsService.existsByUsername("Potato@email.com"));

    }

    //TODO make more comprehensive test
    @Test
    fun testFindALl(){

    }

    @Test
    fun testLoadByUsername(){
        //assertNotNull(capitalUserDetailsService.loadUserByUsername("u"));
        //assertNull(capitalUserDetailsService.loadUserByUsername("Not exists"));
    }

    @Test
    fun testSave(){

    }

}
