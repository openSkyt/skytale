package org.openskyt.skytale.security;

import org.openskyt.skytale.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepo;

    @Autowired
    public SecurityConfig(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .formLogin(form -> form
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .authorizeHttpRequests(r -> {
                    r.requestMatchers("/stylesheets/**", "/scripts/**", "/login", "/register").permitAll();
                    r.anyRequest().authenticated();
                })
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
