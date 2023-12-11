package org.openskyt.skytale.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/login" , "/stylesheets/**", "/scripts/**").permitAll();
                    request.anyRequest().authenticated();
                })
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
        //TODO customize
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
                .username("MarekZ")
                .password(passwordEncoder().encode("kokot"))
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
