package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@ThreadSafe
public class RuleMem implements RuleRepository {

    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    public RuleMem() {
        save(new Rule(1, "Статья. 1"));
        save(new Rule(2, "Статья. 2"));
        save(new Rule(3, "Статья. 3"));
    }

    @Override
    public Set<Rule> findById(String[] id) {
        Set<Rule> rule = null;
        if (id != null) {
            rule = Arrays.stream(id)
                    .map(x -> rules.get(Integer.parseInt(x)))
                    .collect(Collectors.toSet());
        }
        return rule;
    }

    @Override
    public Rule save(Rule rule) {
        return rules.put(rule.getId(), rule);
    }

    @Override
    public Collection<Rule> findAll() {
        return rules.values();
    }
}
