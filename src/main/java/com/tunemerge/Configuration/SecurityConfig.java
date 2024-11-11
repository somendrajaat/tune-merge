package com.tunemerge.Configuration;

import com.tunemerge.Service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
        @Autowired
        private UserDetailServiceImpl userDetailService;
   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    return http.authorizeHttpRequests(request -> request
//                    .requestMatchers("/home/login", "/home/register").permitAll() // Public access to login and register
//                    .requestMatchers("/Spotify/**", "/Amazon/**").authenticated() // Secured access
//                    .anyRequest().authenticated()) // All other requests need authentication
//            .formLogin(Customizer.withDefaults())
//            .csrf(AbstractHttpConfigurer::disable)
//            .build();
       return http
               .authorizeHttpRequests(auth-> auth
                       .anyRequest().permitAll()).csrf(AbstractHttpConfigurer::disable).build();
        }


    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
