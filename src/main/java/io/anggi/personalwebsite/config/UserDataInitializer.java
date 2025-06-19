package io.anggi.personalwebsite.config;

import io.anggi.personalwebsite.model.User;
import io.anggi.personalwebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Inject the username from application.properties or environment variables
    @Value("${app.initial-user.username:user}") // Defaults to 'user' if not set
    private String initialUsername;

    // Inject the password from application.properties or environment variables
    @Value("${app.initial-user.password:password}") // Defaults to 'password' if not set
    private String initialPassword;

    public UserDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Check if the user already exists to prevent re-creating it
        if (userRepository.findByUsername(initialUsername).isEmpty()) {
            System.out.println("Initial user not found, creating user: " + initialUsername);

            User initialUser = new User();
            initialUser.setUsername(initialUsername);
            initialUser.setPassword(passwordEncoder.encode(initialPassword));
            initialUser.setRole("ROLE_USER"); // Or another default role
            userRepository.save(initialUser);

            System.out.println("Initial user '" + initialUsername + "' created successfully.");
        } else {
            System.out.println("Initial user '" + initialUsername + "' already exists. Skipping creation.");
        }
    }
}
