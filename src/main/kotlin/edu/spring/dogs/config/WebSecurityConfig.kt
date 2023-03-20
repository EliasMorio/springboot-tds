package edu.spring.dogs.config

import edu.spring.dogs.services.DbUserService
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true
)
class WebSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests()
            .requestMatchers("/init/**", "/login/**").permitAll()
            .requestMatchers(PathRequest.toH2Console()).permitAll() // (1)
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .requestMatchers("/master/**").hasAnyAuthority("ADMIN", "MANAGER_MASTER")
            .requestMatchers("/dog/**").hasAnyAuthority("ADMIN", "MANAGER_DOG")
            .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
            .anyRequest().authenticated()     // Doit arriver après les requestMatchers
            .and()
            .exceptionHandling().accessDeniedPage("/403")
            .and()
            .formLogin().loginPage("/login").successForwardUrl("/")
            .and()
            .headers().frameOptions().sameOrigin()
            .and()
            .csrf().disable()

        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService? {
        return DbUserService()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }

}