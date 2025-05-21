package dev.carloscastano.get.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para APIs REST
                .httpBasic(basic -> basic.disable()) // Deshabilitar login básico
                .formLogin(form -> form.disable()); // Sin formulario de login

        return http.build();
    }
}