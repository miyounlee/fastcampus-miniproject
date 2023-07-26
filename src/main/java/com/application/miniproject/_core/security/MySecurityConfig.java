package com.application.miniproject._core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@Configuration
public class MySecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.cors().configurationSource(configurationSource());

        http.formLogin().disable(); // Spring Security가 기본적으로 제공하는 formLogin 기능을 사용하지 않겠다
        http.httpBasic().disable(); // 매 요청마다 id, pwd를 보내는 방식으로 인증하는 httpBasic를 사용하지 않겠다

        http.headers().frameOptions().disable(); // iframe 차단

        return http.build();
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(List.of("*")); // method 전부 허용
        configuration.setAllowedOriginPatterns(List.of("*")); // IP 전부 허용
        configuration.setAllowedHeaders(List.of("*")); // 헤더 허용
        configuration.setAllowCredentials(true); // 자바스크립트 응답을 처리할 수 있게 할지 설정(ajax, axios)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
