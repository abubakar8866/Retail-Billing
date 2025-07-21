package com.abubakar.billingSoftware.filters;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.abubakar.billingSoftware.service.iml.AppUserDetailsService;
import com.abubakar.billingSoftware.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final AppUserDetailsService appUserDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwt = null;

        // 1. Extract JWT
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);

            if (jwt != null && !jwt.trim().isEmpty()) {
                try {
                    email = jwtUtil.extractUsername(jwt);
                } catch (Exception e) {
                    // Invalid or expired token — don't set email
                    email = null;
                }
            }
        }

        // 2. If username is found and no authentication exists yet
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = appUserDetailsService.loadUserByUsername(email);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                // Now it's safe to extract roles
                List<String> roles = jwtUtil.extractRoles(jwt);
                if (roles == null) roles = List.of();

                System.out.println("Extracted roles from JWT: " + roles);

                var authorities = roles.stream()
                    .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(role))
                    .toList();

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Always continue the filter chain
        filterChain.doFilter(request, response);

        System.out.println("Final Auth: " + SecurityContextHolder.getContext().getAuthentication());
    }
}
