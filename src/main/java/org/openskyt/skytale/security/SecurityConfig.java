package org.openskyt.skytale.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .authorizeHttpRequests(r -> {
                    r.requestMatchers("/h2-console/**", "/stylesheets/**", "/scripts/**").permitAll();
                    r.requestMatchers("/login").permitAll();
                    r.anyRequest().authenticated();
                })
                .sessionManagement(sessionAuthenticationStrategy ->
                        sessionAuthenticationStrategy.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails Pavel = User
                .builder()
                .username("Pavel")
                .password(passwordEncoder().encode("kokot"))
                .authorities("admin")
                .build();

        UserDetails Ramez = User
                .builder()
                .username("Ramez")
                .password(passwordEncoder().encode("kokot"))
                .authorities("admin")
                .build();

        UserDetails Dan = User
                .builder()
                .username("Dan")
                .password(passwordEncoder().encode("kokot"))
                .authorities("admin")
                .build();

        UserDetails MarekL = User
                .builder()
                .username("MarekL")
                .password(passwordEncoder().encode("kokot"))
                .authorities("admin")
                .build();

        UserDetails MarekZ = User
                .builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .authorities("admin")
                .build();

        UserDetails Vasek = User
                .builder()
                .username("Vasek")
                .password(passwordEncoder().encode("kokot"))
                .authorities("admin")
                .build();

        UserDetails Ales = User
                .builder()
                .username("perryCZ")
                .password(passwordEncoder().encode("kokot"))
                .authorities("admin")
                .build();

        return new InMemoryUserDetailsManager(Pavel, Ramez, Dan, MarekL, MarekZ, Vasek, Ales);
    }
}
