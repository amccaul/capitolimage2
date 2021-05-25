package com.example.capitol

import com.example.capitol.repository.CapitolUserRepository
import org.apache.catalina.Context
import org.apache.catalina.connector.Connector
import org.apache.tomcat.util.descriptor.web.SecurityCollection
import org.apache.tomcat.util.descriptor.web.SecurityConstraint
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.example.capitol.config",
   "com.example.capitol.repository")
class CapitolApplication

fun main(args: Array<String>) {
   runApplication<CapitolApplication>(*args)

}
//TODO redirect 8082 HTTP to 8443 HTTPS

