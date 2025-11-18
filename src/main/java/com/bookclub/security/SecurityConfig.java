package com.bookclub.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // allow public routes
                .antMatchers("/", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                // protect API and wishlist pages
                .antMatchers("/api/**", "/wishlist/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                // ✅ Disable CSRF so fetch() DELETE calls work
                .and()
                .csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/monthly-books/**").hasRole("ADMIN")
                .antMatchers("/api/**", "/wishlist/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .csrf().disable();

    }
}