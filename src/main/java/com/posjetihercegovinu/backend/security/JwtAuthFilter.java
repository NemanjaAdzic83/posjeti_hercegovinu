package com.posjetihercegovinu.backend.security;

import com.posjetihercegovinu.backend.entity.User;
import com.posjetihercegovinu.backend.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// Filter koji se izvrsava na svaki zahtjev
// Cita zaglavlje Authorization: Bearer<token>, provjeri tkoen i kaze Springu ko je korisnik
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    // Poziva se jednom po zahtjevu.A ko zahtjec ima ispravan token, nalazi korisnika u bazi i
    // postavlja ga kao prijavljenog(s ulogom ROLE_(uloga). Ako nema tokena ili je neispravan ,
    // samo propusta zahtjev kao anoniman; SecurityConfig tada odlucuje
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {


        String header =  request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            String email = jwtService.extractEmail(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepository.findByEmail(email).orElse(null);

                if (user != null) {
                    var auth = new UsernamePasswordAuthenticationToken(
                            user.getEmail(), null,
                            List.of(new SimpleGrantedAuthority("ROLE_" +  user.getRole().name()))
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

        }
        chain.doFilter(request, response);




    }
}
