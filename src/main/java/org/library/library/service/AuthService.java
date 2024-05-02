package org.library.library.service;

import lombok.RequiredArgsConstructor;
import org.library.library.entity.Roles;
import org.library.library.entity.User;
import org.library.library.exception.DuplicateEmailException;
import org.library.library.exception.InvalidEmailException;
import org.library.library.exception.InvalidPasswordException;
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

    public String registerNewUser(String email, String password) {
        if (password.length() < 6) {
            throw new InvalidPasswordException(String.valueOf(password.length()));
        }
        if (email.isEmpty()) {
            throw new InvalidEmailException("0");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            System.out.println("here");
            throw new DuplicateEmailException(email);
        }

        var encodedPassword = passwordEncoder.encode(password);
        User newUser = User.builder()
                           .email(email)
                           .password(encodedPassword)
                           .roles(Roles.ROLE_USER.name())
                           .build();
        User registeredUser = userRepository.save(newUser);
        return "New user registered successfully with id: " + registeredUser.getId();
    }
}
