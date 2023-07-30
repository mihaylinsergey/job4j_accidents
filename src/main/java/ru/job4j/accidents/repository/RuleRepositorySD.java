package ru.job4j.accidents.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;
import java.util.List;
import java.util.Set;

@Primary
public interface RuleRepositorySD extends CrudRepository<Rule, Integer> {

    Set<Rule> findByIdIn(List<Integer> id);
}
