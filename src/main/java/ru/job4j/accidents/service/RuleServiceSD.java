package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepositorySD;

import java.util.*;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
@Primary
public class RuleServiceSD {

    private final RuleRepositorySD ruleRepositorySD;

    public Collection<Rule> findAll() {
        return ruleRepositorySD.findAll();
    }

    public Set<Rule> findByIdIn(List<Integer> id) {
        return ruleRepositorySD.findByIdIn(id);
    }
}
