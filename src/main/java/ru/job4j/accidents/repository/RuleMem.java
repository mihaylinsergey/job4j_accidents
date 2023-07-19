package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(rules.get(id));
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
