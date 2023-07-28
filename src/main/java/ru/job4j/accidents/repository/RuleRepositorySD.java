package ru.job4j.accidents.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

@Primary
public interface RuleRepositorySD extends CrudRepository<Rule, Integer> {
}
