package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;
import ru.job4j.accidents.repository.RuleRepository;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Primary
@ThreadSafe
public class AccidentServiceHibernate implements AccidentService {
    private final AccidentRepository accidentRepository;
    private final AccidentTypeRepository accidentTypeRepository;
    private final RuleRepository ruleRepository;

    @Override
    public Collection<Accident> findAll() {
        return accidentRepository.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public Accident save(Accident accident, HttpServletRequest req) {
        return accidentRepository.save(getAccidentWithTypeAndRules(accident, req));
    }

    @Override
    public boolean update(Accident accident, HttpServletRequest req) {
        return accidentRepository.update(getAccidentWithTypeAndRules(accident, req));
    }

    @Override
    public boolean delete(int id) {
        return accidentRepository.delete(id);
    }

    private Accident getAccidentWithTypeAndRules(Accident accident, HttpServletRequest req) {
        AccidentType type = accidentTypeRepository.findById(accident.getType().getId()).get();
        accident.setType(type);
        String[] ids = req.getParameterValues("rIds");
        Set<Rule> rules = ruleRepository.findById(ids);
        accident.setRules(rules);
        return accident;
    }
}
