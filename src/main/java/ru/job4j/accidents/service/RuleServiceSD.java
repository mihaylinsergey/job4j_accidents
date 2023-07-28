package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepositorySD;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
public class RuleServiceSD {

    private final RuleRepositorySD ruleRepositorySD;

    public Collection<Rule> findAll() {
        return (Collection<Rule>) ruleRepositorySD.findAll();
    }

    public Set<Rule> findById(String[] id) {
        List<Integer> list = Arrays.stream(id).map(Integer :: parseInt).collect(Collectors.toList());
        return new HashSet<>((Collection) ruleRepositorySD.findAllById(list));
    }
}
