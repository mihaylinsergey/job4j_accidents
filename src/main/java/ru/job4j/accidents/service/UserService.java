package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserRepository;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> save(User user) {
        Optional<User> rsl;
        try {
            rsl = Optional.of(userRepository.save(user));
        } catch (Exception e) {
            rsl = Optional.empty();
        }
        return rsl;
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) {
         return userRepository.findByUsernameAndPassword(username, password);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
