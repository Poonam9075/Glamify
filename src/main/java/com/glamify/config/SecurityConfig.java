package com.glamify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import com.glamify.security.JwtFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,
	                                       JwtFilter jwtFilter) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers(
	                "/api/auth/**",
	                "/swagger-ui/**",
	                "/v3/api-docs/**"
	            ).permitAll()
	            .requestMatchers("/api/admin/**").hasRole("ADMIN")
	            .requestMatchers("/api/customer/**").hasRole("CUSTOMER")
	            .requestMatchers("/api/professional/**").hasRole("PROFESSIONAL")
	            .anyRequest().authenticated()
	        )
	        // ✅ NOW THIS WORKS
	        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}

}
