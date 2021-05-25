package rc.bootsecurity.security

import com.example.capitol.config.CapitolUserDetailsService
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import java.lang.Exception

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
            .antMatchers( HttpMethod.GET,"/api/contact")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .anyRequest().permitAll()
            //.antMatchers(HttpMethod.GET, "/api/contact").permitAll()
            .and()
            .csrf().disable()

            .httpBasic()
    //http
            //.cors()
           // .and()
            //.csrf().disable()
          //  .authorizeRequests()
            //.anyRequest().permitAll()
            //.antMatchers("/api/contact").permitAll()
            //.anyRequest().authenticated()
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
