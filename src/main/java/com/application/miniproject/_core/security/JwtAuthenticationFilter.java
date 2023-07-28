package com.application.miniproject._core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith(JwtProvider.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = header.replaceAll(JwtProvider.TOKEN_PREFIX, "");
        try {
            jwtVerifyToken(jwt);
        } catch (SignatureException | ExpiredJwtException e) {
            // TODO 공통 응답 생성
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void jwtVerifyToken(String jwt) {
        jwtProvider.verifyToken(jwt);
        log.info("토큰 인증 성공");
        SecurityContextHolder.getContext().setAuthentication(jwtProvider.authentication(jwt));
        log.info("인증 객체 생성");
    }
}