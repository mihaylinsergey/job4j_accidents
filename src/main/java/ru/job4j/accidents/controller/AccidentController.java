package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.*;
import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@RequestMapping("/accident")
public class AccidentController {
    private final AccidentServiceSD accidentService;

    private final AccidentTypeServiceSD accidentTypeService;

    private final RuleServiceSD ruleService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "/accident/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        accidentService.create(accident, req);
        return "redirect:/accident/index";
    }

    @GetMapping("/updateAccident")
    public String getById(@RequestParam("id") int id, Model model) {
        var accidentOptional = accidentService.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Инцидент не найден");
            return "error/404";
        }
        model.addAttribute("accident", accidentOptional.get());
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "/accident/updateAccident";
    }

    @PostMapping("/updateAndSaveAccident")
    public String updateAndSaveAccident(@ModelAttribute Accident accident, Model model, HttpServletRequest req) {
        if (!accidentService.update(accident, req)) {
            model.addAttribute("message", "Изменить инцидент %s не удалось".formatted(accident.getName()));
            return "error/404";
        }
        return "redirect:/accident/index";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id, Model model) {
        if (!accidentService.delete(id)) {
            model.addAttribute("message", "Инцидент не удалось удалить");
            return "error/404";
        }
        return "redirect:/accident/index";
    }
}