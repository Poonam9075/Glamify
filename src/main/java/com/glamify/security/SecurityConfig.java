package com.glamify.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
        System.out.println(">>> SecurityConfig LOADED <<<");
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})
            .authorizeHttpRequests(auth -> auth

                // 🔓 STATIC FRONTEND FILES (THIS FIXES 403)
                .requestMatchers(
                		"/",
                        "/login.html",
                        "/admin.html",
                        "/customer.html",
                        "/professional.html",
                        "/navbar.html",
                        "/api.js",
                        "/favicon.ico",
                        "/css/**",
                        "/js/**",
                        "/images/**"
                ).permitAll()

                // 🔓 AUTH + SWAGGER
                .requestMatchers(
                    "/api/auth/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html"
                ).permitAll()

                // 🔓 PREFLIGHT
                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()

                // 🔐 ROLE-BASED APIs
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/customer/**").hasRole("CUSTOMER")
                .requestMatchers("/api/professional/**").hasRole("PROFESSIONAL")

                // 🔐 EVERYTHING ELSE
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
