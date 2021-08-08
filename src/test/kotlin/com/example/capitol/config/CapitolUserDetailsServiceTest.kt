package com.example.capitol.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UserDetails

@SpringBootTest
internal class CapitolUserDetailsServiceTest {
    @Autowired
    private lateinit var capitalUserDetailsService:CapitolUserDetailsService;

    @Test
    fun contextLoads(){
        assertThat(capitalUserDetailsService).isNotNull();
    }

    @Test
    fun testExists(){
        //ensure nonexistent username is not found and returns false
        assertFalse(capitalUserDetailsService.existsByUsername("Nonexistent username"));
        //ensure existent username is found and returns true
        assertTrue(capitalUserDetailsService.existsByUsername("u"));

    }

    //TODO make more comprehensive test
    @Test
    fun testFindALl(){
     //  assertNotNull(testFindALl())
    }

    @Test
    fun testLoadByUsername(){
        assertNotNull(capitalUserDetailsService.loadUserByUsername("u"));
        assertNull(capitalUserDetailsService.loadUserByUsername("Not exists"));
    }

    @Test
    fun testSave(){

    }

}
