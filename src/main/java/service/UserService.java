package service;

import model.User;
import repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Регистрация нового пользователя
    public User registerUser(String username, String password, String role) {
        // Проверка, существует ли пользователь
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalStateException("Пользователь уже существует!");
        }

        // Создаем нового пользователя
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Хешируем пароль
        user.setRole(role);

        System.out.println("User created: " + user); // Для отладки

        return userRepository.save(user); // Сохраняем пользователя в БД
    }

    // Поиск пользователя по имени
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Ищем пользователя в базе данных
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с именем " + username + " не найден"));

        // Возвращаем объект UserDetails для Spring Security
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole()) // Указываем роли
                .build();
    }
}