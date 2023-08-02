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

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) {
<<<<<<< HEAD
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@" + userRepository.findByUsernameAndPassword(username, password));
        return userRepository.findByUsernameAndPassword(username, password);
=======
         return userRepository.findByUsernameAndPassword(username, password);
>>>>>>> 2f3d8a4d012994a9c779d35f967d04513f932811
    }
}
