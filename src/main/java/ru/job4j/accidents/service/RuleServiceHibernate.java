package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;
import java.util.Collection;
import java.util.Set;

/*@Service
@ThreadSafe*/
@AllArgsConstructor
public class RuleServiceHibernate implements RuleService {

    private final RuleRepository ruleRepository;

    @Override
    public Set<Rule> findById(String[] id) {
        return ruleRepository.findById(id);
    }

    @Override
    public Rule save(Rule rule) {
        return ruleRepository.save(rule);
    }

    @Override
    public Collection<Rule> findAll() {
        return ruleRepository.findAll();
    }
}
