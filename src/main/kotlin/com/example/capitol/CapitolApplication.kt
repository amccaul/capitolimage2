package com.example.capitol

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.servlet.config.annotation.CorsRegistry

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer




@SpringBootApplication
class CapitolApplication


fun main(args: Array<String>) {
   runApplication<CapitolApplication>(*args)
}





