package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class.getName());

    public Optional<User> save(User user) {
        Optional<User> rsl = Optional.empty();
        try {
            rsl = Optional.of(userRepository.save(user));
        } catch (Exception e) {
            LOG.error("Error!", e);
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
