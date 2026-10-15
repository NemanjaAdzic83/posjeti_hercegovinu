package com.posjetihercegovinu.backend.config;

import com.posjetihercegovinu.backend.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Pravila ko smije gdje
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter  jwtAuthFilter;

    // Definise lanac sigurnosnih pravila
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // csrf. zastita je za apliakcije s kolacicima, kod token anije potrebna
                .csrf(AbstractHttpConfigurer::disable)
                // Server ne pamti sesije, svaki zahtjev nosi svoj token
                .sessionManagement( s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Bez prijave vracamo 401
                .exceptionHandling( e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                        .accessDeniedHandler((request, response, ex) ->
                                response.setStatus(HttpStatus.FORBIDDEN.value())))
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/places/**", "/api/categories/**", "/api/accommodations/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/places/**", "/api/categories/**", "/api/accommodations/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/places/**", "/api/categories/**", "/api/accommodations/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/places/**", "/api/categories/**", "/api/accommodations/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                // Nas filter ide prije standardnog filtera za prijavu
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
