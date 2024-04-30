package org.library.library.service;

import lombok.RequiredArgsConstructor;
import org.library.library.model.LoginResponse;
import org.library.library.security.JwtIssuer;
import org.library.library.security.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    public LoginResponse attemptLogin(String email, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();

        var roles = principal.getAuthorities().stream()
                             .map(GrantedAuthority::getAuthority)
                             .toList();

        var token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(), roles);

        return LoginResponse.builder()
                            .token(token)
                            .build();
    }
}
