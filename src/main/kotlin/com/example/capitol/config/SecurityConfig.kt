package com.example.capitol.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration (var capitolUserDetailsService:CapitolUserDetailsService,
                             @Autowired var passwordEncoder: PasswordEncoder):
    WebSecurityConfigurerAdapter() {
    //ResourceServerConfigurerAdapter() {


    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }


    override fun configure(web: WebSecurity) {
        web
            .ignoring()
            .antMatchers(HttpMethod.GET, "/api/contact")
    }

    //TODO update end point security
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .cors()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET,"/api/user/exists").permitAll()
            .antMatchers(HttpMethod.PUT,"/api/user/save").permitAll()
            .antMatchers(HttpMethod.POST,"/api/contact").permitAll()
            .anyRequest().authenticated()
            .and()
            .csrf().disable()
            .httpBasic()

/*http
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            .and()
            .csrf().disable()
            .httpBasic()*/
        //.antMatchers(HttpMethod.GET, "/api/contact").permitAll()
*/
        //http
        //.cors()
        // .and()
        //.csrf().disable()
        //  .authorizeRequests()
        //.anyRequest().permitAll()
        //.antMatchers("/api/contact").permitAll()
        //czczzcz  d
        //.and()
        // .formLogin().loginPage("/login.html").permitAll()
        //.and()
        //.logout().permitAll()
        //.and()
        // .exceptionHandling().accessDeniedPage("/accessdenied")
        //.and()
        // .httpBasic()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val daoAuthenticationProvider = DaoAuthenticationProvider()
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder)
        daoAuthenticationProvider.setUserDetailsService(capitolUserDetailsService)
        return daoAuthenticationProvider
    }
}



