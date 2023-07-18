package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping("/createAccident")
    public String viewCreateAccident() {
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidentService.save(accident);
        return "redirect:/index";
    }

    @GetMapping("/updateAccident")
    public String getById(@RequestParam("id") int id, Model model) {
        var accidentOptional = accidentService.findById(id);
        model.addAttribute("accident", accidentOptional.get());
        return "updateAccident";
    }

    @PostMapping("/updateAndSaveAccident")
    public String updateAndSaveAccident(@ModelAttribute Accident accident, Model model) {
        accidentService.update(accident);
        return "redirect:/index";
    }
}