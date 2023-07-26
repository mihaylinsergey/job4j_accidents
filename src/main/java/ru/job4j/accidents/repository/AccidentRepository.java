package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface AccidentRepository {

    Collection<Accident> findAll();

    Optional<Accident> findById(int id);

    Accident save(Accident accident);

    Accident save(Accident accident, Set<Rule> rules);

    boolean update(Accident accident);

    boolean update(Accident accident, Set<Rule> rules);

    boolean delete(int id);
}
