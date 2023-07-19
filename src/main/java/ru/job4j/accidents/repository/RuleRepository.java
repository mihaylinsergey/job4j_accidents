package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;
import java.util.Collection;
import java.util.Optional;

public interface RuleRepository {

    Optional<Rule> findById(int id);

    Rule save(Rule rule);

    Collection<Rule> findAll();
}
