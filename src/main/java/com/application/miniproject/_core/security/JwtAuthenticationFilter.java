package com.application.miniproject._core.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith(JwtProvider.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = header.replaceAll(JwtProvider.TOKEN_PREFIX, "");

        jwtProvider.verifyToken(jwt);

        SecurityContextHolder.getContext().setAuthentication(jwtProvider.authentication(jwt));

        filterChain.doFilter(request, response);
    }
}