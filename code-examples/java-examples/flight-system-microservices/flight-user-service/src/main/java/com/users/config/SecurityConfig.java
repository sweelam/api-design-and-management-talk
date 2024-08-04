package com.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    public static final String ADMIN = "SweProfile";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeConfig ->
                    authorizeConfig.requestMatchers("/api/users/**").authenticated()
                            .requestMatchers("/").permitAll()
                            .requestMatchers("/error").permitAll()
                            .requestMatchers("/favicon.ico").permitAll()
                            .requestMatchers("/api/public/users/**").permitAll()
                )
                .formLogin(withDefaults())
//                .addFilterBefore(new HeaderBasedFilter() , UsernamePasswordAuthenticationFilter.class)
//                .authenticationProvider(new SuperAdminAuthenticationProvider())
                .sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
