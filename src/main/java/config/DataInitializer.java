package config;

import model.User;
import service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        if (userService.findByUsername("admin").isEmpty()) {
            userService.registerUser("admin", "admin12345", "ADMIN");
        }
        if (userService.findByUsername("user").isEmpty()) {
            userService.registerUser("user", "user12345", "USER");
        }
    }
}