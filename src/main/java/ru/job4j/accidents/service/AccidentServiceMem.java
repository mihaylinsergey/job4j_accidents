package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
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
@ThreadSafe
public class AccidentServiceMem implements AccidentService {

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
        Accident accidentWithType = getAccidentWithType(accident);
        Accident accidentWithTypeAndRule = getAccidentWithRule(accidentWithType, req);
        return accidentRepository.save(accidentWithTypeAndRule);
    }

    @Override
    public boolean update(Accident accident, HttpServletRequest req) {
        Accident accidentWithType = getAccidentWithType(accident);
        Accident accidentWithTypeAndRule = getAccidentWithRule(accidentWithType, req);
        return accidentRepository.update(accidentWithTypeAndRule);
    }

    @Override
    public boolean delete(int id) {
        return accidentRepository.delete(id);
    }

    private Accident getAccidentWithType(Accident accident) {
        int typeId = accident.getType().getId();
        var type = accidentTypeRepository.findById(typeId).get();
        accident.setType(type);
        return accident;
    }

    private Accident getAccidentWithRule(Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        Set<Rule> rule = ruleRepository.findById(ids);
        accident.setRules(rule);
        return accident;
    }
}
