package com.football.conference.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired private JWTFilter filter;
    @Autowired private MyClubDetailsService cds;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .httpBasic(httpBasic -> httpBasic.disable())
            .cors(cors -> {})
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers(HttpMethod.POST,"/login").permitAll()
                .requestMatchers(HttpMethod.POST,"/club").permitAll()
                .requestMatchers(HttpMethod.POST,"/club/{clubId}").authenticated()
                .requestMatchers(HttpMethod.GET,"/club/{clubId}").authenticated()
                .requestMatchers(HttpMethod.PUT,"/club/{clubId}").authenticated()
                .requestMatchers(HttpMethod.GET,"/club").authenticated()
                .requestMatchers(HttpMethod.POST,"/club/{clubId}/player").authenticated()
                .requestMatchers(HttpMethod.GET,"/club/{clubId}/player").authenticated()
                .requestMatchers(HttpMethod.GET,"/club/{clubId}/player/{playerId}").authenticated()
                .requestMatchers(HttpMethod.PUT,"/club/{clubId}/player/{playerId}").authenticated()
                .requestMatchers(HttpMethod.DELETE,"/club/{clubId}/player/{playerId}").authenticated()
                .anyRequest().authenticated()
            )
            .userDetailsService(cds)
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> 
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}