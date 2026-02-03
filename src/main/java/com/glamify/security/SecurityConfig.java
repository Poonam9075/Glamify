package com.glamify.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
//        this.jwtFilter = jwtFilter;
        System.out.println(">>> SecurityConfig LOADED <<<");
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            // ENABLE CORS
            .cors(cors -> {})
//            .sessionManagement(sess ->
//	            sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//	        )
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            .authorizeHttpRequests(auth -> auth

        		// ALLOW PREFLIGHT
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            		
                // STATIC FRONTEND FILES (THIS FIXES 403)
                .requestMatchers(
                		"/",
                		"/*.html",
                        "/login.html",
                        "/admin.html",
                        "/customer.html",
                        "/professional.html",
                        "/navbar.html",
                        "/api.js",                        
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/*.css",
                        "/favicon.ico"
                ).permitAll()

                // AUTH + SWAGGER
                .requestMatchers(
                    "/api/auth/**",
                    "/register/**",
                    "/login/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html"
                ).permitAll()

                // ROLE-BASED APIs
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/customer/**").hasRole("CUSTOMER")
                .requestMatchers("/api/payments/**").hasRole("CUSTOMER")
                .requestMatchers("/api/invoices/**").hasRole("CUSTOMER")
                .requestMatchers("/api/professional/**").hasRole("PROFESSIONAL")

                // EVERYTHING ELSE
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
