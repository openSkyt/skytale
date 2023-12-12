package org.openskyt.skytale.security;

import org.openskyt.skytale.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

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
                    r.requestMatchers("/h2-console/**", "/stylesheets/**", "/scripts/**", "/login", "/register").permitAll();
                    r.anyRequest().authenticated();
                })
                .sessionManagement(sessionAuthenticationStrategy ->
                        sessionAuthenticationStrategy.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            Optional<org.openskyt.skytale.models.User> user = userRepo.findByName(username);
            if (user.isPresent()) {
                return User.withUsername(username)
                        .password(user.get().getPassword())
                        .roles("USER")
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found: " + username);
            }
        };
    }

}
