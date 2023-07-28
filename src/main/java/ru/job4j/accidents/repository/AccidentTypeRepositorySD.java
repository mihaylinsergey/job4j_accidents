package ru.job4j.accidents.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.AccidentType;

@Primary
public interface AccidentTypeRepositorySD extends CrudRepository<AccidentType, Integer> {
}
