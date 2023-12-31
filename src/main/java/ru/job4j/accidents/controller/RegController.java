package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.AuthorityService;
import ru.job4j.accidents.service.UserService;

@Controller
@AllArgsConstructor
@RequestMapping("/accident")
public class RegController {

    private final PasswordEncoder encoder;
    private final UserService users;
    private final AuthorityService authorities;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        if (users.save(user).isPresent()) {
            return "redirect:/accident/login";
        }
        model.addAttribute("message", "Данный логин уже существует");
        return "/error/404";

    }

    @GetMapping("/reg")
    public String regPage() {
        return "/accident/reg";
    }
}
