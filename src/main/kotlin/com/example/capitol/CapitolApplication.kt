package com.example.capitol

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.example.capitol.config",
   "com.example.capitol.controller",
   "com.example.capitol.entity",
   "com.example.capitol.mail",
   "com.example.capitol.repository",
   "com.example.capitol.viewmodel")
class CapitolApplication

fun main(args: Array<String>) {
   runApplication<CapitolApplication>(*args)

}

