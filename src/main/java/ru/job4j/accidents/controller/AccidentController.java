package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/accident")
public class AccidentController {
    private final AccidentService accidentService;

    private final AccidentTypeService accidentTypeService;

    private final RuleService ruleService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "/accident/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        int typeId = accident.getType().getId();
        var type = accidentTypeService.findById(typeId).get();
        String[] ids = req.getParameterValues("rIds");
        Set<Rule> rules = null;
        if (ids != null) {
            rules = Arrays.stream(ids)
                    .map(x -> ruleService.findById(Integer.parseInt(x)).get())
                    .collect(Collectors.toSet());
        }
        accident.setType(type);
        accident.setRules(rules);
        accidentService.save(accident);
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
        int typeId = accident.getType().getId();
        var type = accidentTypeService.findById(typeId).get();
        String[] ids = req.getParameterValues("rIds");
        Set<Rule> rules = null;
        if (ids != null) {
            rules = Arrays.stream(ids)
                    .map(x -> ruleService.findById(Integer.parseInt(x)).get())
                    .collect(Collectors.toSet());
        }
        accident.setType(type);
        accident.setRules(rules);
        if (!accidentService.update(accident)) {
            model.addAttribute("message", "Изменить инцидент %s не удалось".formatted(accident.getName()));
            return "error/404";
        }
        return "redirect:/accident/index";
    }
}