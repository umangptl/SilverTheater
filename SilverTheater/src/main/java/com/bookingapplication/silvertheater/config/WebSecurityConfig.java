package com.bookingapplication.silvertheater.config;

import com.bookingapplication.silvertheater.Service.Impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig { // "adminTheater" password //"yourmember" // Your
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/AdminManagement/**").hasRole("THEATRE_EMPLOYEE")
                        .requestMatchers("/AdminManagement/deleteMultiplex/**").hasRole("THEATRE_EMPLOYEE")
                        .requestMatchers("/AdminManagement/deleteMovie/**").hasRole("THEATRE_EMPLOYEE")
                        .requestMatchers("/AdminManagement/deleteShow/**").hasRole("THEATRE_EMPLOYEE")
                        .requestMatchers("/AdminManagement/updateMultiplex/**").hasRole("THEATRE_EMPLOYEE")
                        .requestMatchers("/AdminManagement/updateMovie/**").hasRole("THEATRE_EMPLOYEE")
                        .requestMatchers("/AdminManagement/updateShow/**").hasRole("THEATRE_EMPLOYEE")
                        .requestMatchers("/AdminManagement/addMultiplex/**").hasRole("THEATRE_EMPLOYEE")
                        .requestMatchers("/AdminManagement/addMovie/**").hasRole("THEATRE_EMPLOYEE")
                        .requestMatchers("/AdminManagement/addShow/**").hasRole("THEATRE_EMPLOYEE")
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .userDetailsService(userDetailsService());

        http.csrf(AbstractHttpConfigurer::disable).headers(headers ->
        { headers.cacheControl(HeadersConfigurer.CacheControlConfig::disable);
        headers.disable();});

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
