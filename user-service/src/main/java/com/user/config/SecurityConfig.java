package com.user.config;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // disable CSRF for simplicity now
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/users/*", "/api/users/signup", "/api/users/login").permitAll() // public
//                        .anyRequest().authenticated() // everything else needs auth
//                )
//                .httpBasic(httpBasic -> {}); // keep simple auth until we integrate OAuth2/JWT
//
//        return http.build();
//    }
//}
