package com.bookclub.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // -------------------------
    // In-memory users
    // -------------------------
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();

        UserDetails testuser01 = User.withUsername("testuser01")
                .password(passwordEncoder.encode("password01"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("password"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, testuser01, admin);
    }

    // BCrypt encoder required by Spring Security
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // -------------------------
    // URL security rules
    // -------------------------
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                // Public pages
                .antMatchers("/", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                // Admin-only (Assignment 9)
                .antMatchers("/monthly-books/**").hasRole("ADMIN")
                // Authenticated users
                .antMatchers("/api/**", "/wishlist/**").authenticated()
                .anyRequest().authenticated()
                .and()
                // Login page
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                // Logout allowed for everyone
                .logout()
                .permitAll()
                .and()
                // Disable CSRF so your JS fetch() DELETE/POST calls work
                .csrf().disable();

        return http.build();
    }
}
