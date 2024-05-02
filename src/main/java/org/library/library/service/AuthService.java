package org.library.library.service;

import lombok.RequiredArgsConstructor;
import org.library.library.entity.Roles;
import org.library.library.entity.User;
import org.library.library.model.LoginRequest;
import org.library.library.model.LoginResponse;
import org.library.library.repository.UserRepository;
import org.library.library.security.JwtIssuer;
import org.library.library.security.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse attemptLogin(String email, String password) {
        if (userRepository.findByEmail(email).isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

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

    public String registerNewUser(LoginRequest registerRequest) {
        var password = passwordEncoder.encode(registerRequest.getPassword());
        User newUser = User.builder()
                           .email(registerRequest.getEmail())
                           .password(password)
                           .roles(Roles.ROLE_USER.name())
                           .build();
        User registeredUser = userRepository.save(newUser);
        return "New user registered successfully with id: " + registeredUser.getId();
    }
}
