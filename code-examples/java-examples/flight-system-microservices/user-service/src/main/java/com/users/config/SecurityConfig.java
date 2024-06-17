package com.users.config;

import com.users.filter.HeaderBasedFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

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
                .addFilterBefore(new HeaderBasedFilter() , UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(new SuperAdminAuthenticationProvider())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password("admin")
                        .roles("ADMIN_USER")
                        .passwordEncoder(p -> passwordEncoder().encode(p))
                        .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
