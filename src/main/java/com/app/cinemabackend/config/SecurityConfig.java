package com.app.cinemabackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Rutas pública (Swagger, Health, Auth y flujo del Cliente)
                .requestMatchers("/", "/api/health", "/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/peliculas/**", "/api/funciones/**", "/api/salas/**", "/api/butacas/**", "/api/dulceria/**", "/api/reservas/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/funciones/bloquear-butacas", "/api/funciones/*/liberar-butacas", "/api/reservas").permitAll()

                // Rutas protegidas (solamente ADMIN puede crear, editar o borrar)
                .requestMatchers(HttpMethod.POST, "/api/peliculas/**", "/api/salas/**", "/api/funciones/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/peliculas/**", "/api/salas/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/peliculas/**", "/api/salas/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/peliculas/**", "/api/salas/**", "/api/funciones/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/peliculas/**", "/api/salas/**", "/api/funciones/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}