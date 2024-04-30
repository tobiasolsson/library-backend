package org.library.library.service;

import org.library.library.entity.Roles;
import org.library.library.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private static final String EXISTING_EMAIL = "test@test.com";
    private static final String ANOTHER_EMAIL = "next@test.com";

    public Optional<User> findByEmail(String email) {
        // TODO: Move this to a database
        if (EXISTING_EMAIL.equalsIgnoreCase(email)) {
            var user = new User(1L, EXISTING_EMAIL, "$2a$12$fcW6o.DArqEgscb7xssHLe6WQVGoa9eUDNgqBwzxBYbkkIEhpZ0lS", Roles.ROLE_ADMIN.name());
            return Optional.of(user);
        } else if (ANOTHER_EMAIL.equalsIgnoreCase(email)) {
            var user = new User(99L, ANOTHER_EMAIL, "$2a$12$fcW6o.DArqEgscb7xssHLe6WQVGoa9eUDNgqBwzxBYbkkIEhpZ0lS", Roles.ROLE_USER.name());
            return Optional.of(user);
        }

        return Optional.empty();
    }
}
