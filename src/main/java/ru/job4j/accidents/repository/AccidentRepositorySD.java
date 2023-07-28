package ru.job4j.accidents.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

@Primary
public interface AccidentRepositorySD extends CrudRepository<Accident, Integer> {
}
