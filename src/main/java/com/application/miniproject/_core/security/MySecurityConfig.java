package com.application.miniproject._core.security;

import com.application.miniproject._core.error.exception.Exception401;
import com.application.miniproject._core.error.exception.Exception403;
import com.application.miniproject._core.util.FilterRespUtil;
import com.application.miniproject._core.util.JwtFilterRespUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class MySecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtFilterRespUtil jwtFilterRespUtil;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.cors().configurationSource(configurationSource());

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.formLogin().disable(); // Spring Security가 기본적으로 제공하는 formLogin 기능을 사용하지 않겠다

        http.httpBasic().disable(); // 매 요청마다 id, pwd를 보내는 방식으로 인증하는 httpBasic를 사용하지 않겠다

        http.headers().frameOptions().sameOrigin(); // iframe 차단

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(jwtFilterRespUtil, JwtAuthenticationFilter.class);

        // 8. 인증 실패 처리
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            FilterRespUtil.unAuthorized(response, new Exception401("인증되지 않았습니다"));
        });

        // 9. 권한 실패 처리
        http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
            FilterRespUtil.forbidden(response, new Exception403("권한이 없습니다"));
        });

        http.authorizeHttpRequests(auth ->
                auth.antMatchers("/user/login", "/user/join", "/user/email", "/healthCheck").permitAll()
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .antMatchers("/**").authenticated());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(List.of("*")); // method 전부 허용
        configuration.setAllowedOriginPatterns(List.of("*")); // IP 전부 허용
        configuration.setAllowedHeaders(List.of("*")); // 헤더 허용
        configuration.setAllowCredentials(true); // 자바스크립트 응답을 처리할 수 있게 할지 설정(ajax, axios)
        configuration.addExposedHeader(HttpHeaders.AUTHORIZATION);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
