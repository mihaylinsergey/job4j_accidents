package ru.job4j.accidents.service;

import org.springframework.context.annotation.Primary;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepositorySD;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
@Primary
public class AccidentServiceSD {

    private final AccidentRepositorySD accidentRepositorySD;
    private final AccidentTypeServiceSD accidentTypeServiceSD;
    private final RuleServiceSD ruleServiceSD;

    public void create(Accident accident, HttpServletRequest req) {
        accidentRepositorySD.save(getAccidentWithTypeAndRules(accident, req));
    }

    public List<Accident> findAll() {
        return StreamSupport.stream(accidentRepositorySD.findAll().spliterator(), false).toList();
    }

    public boolean update(Accident accident, HttpServletRequest req) {
        return accidentRepositorySD.save(getAccidentWithTypeAndRules(accident, req)) != null;
    }

    public Optional<Accident> findById(int id) {
        return accidentRepositorySD.findById(id);
    }

    public boolean delete(int id) {
        accidentRepositorySD.deleteById(id);
        return accidentRepositorySD.findById(id).isEmpty();
    }

    private Accident getAccidentWithTypeAndRules(Accident accident, HttpServletRequest req) {
        AccidentType type = accidentTypeServiceSD.findById(accident.getType().getId()).get();
        accident.setType(type);
        List<Integer> listIds = Arrays.stream(req.getParameterValues("rIds"))
                .map(Integer :: parseInt).toList();
        Set<Rule> rules = ruleServiceSD.findByIdIn(listIds);
        accident.setRules(rules);
        return accident;
    }
}

