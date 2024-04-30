package org.library.library.security;

import lombok.RequiredArgsConstructor;
import org.library.library.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return UserPrincipal.builder()
                            .userId(user.getId())
                            .email(user.getEmail())
                            .authorities(List.of(new SimpleGrantedAuthority(user.getRoles())))
                            .password(user.getPassword())
                            .build();
    }
}
