package com.Glamify.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.Glamify.dto.AuthRequest;
import com.Glamify.dto.AuthResponse;
import com.Glamify.security.JwtUtils;
import com.Glamify.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImple implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public AuthResponse login(AuthRequest request) {

        //  Ask Spring Security to authenticate user
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        //  If authentication is successful, get logged-in user
        UserPrincipal principal =
                (UserPrincipal) authentication.getPrincipal();

        //  Generate JWT token
        String token = jwtUtils.generateToken(principal);

        //  Return response
        return new AuthResponse(
                "Login successful",
                token,
                principal.getUserRole()
        );
    }
}
