package ru.job4j.accidents.service;

import ru.job4j.accidents.model.AccidentType;
import java.util.Collection;
import java.util.Optional;

public interface AccidentTypeService {

    Optional<AccidentType> findById(int id);

    AccidentType save(AccidentType accidentType);

    Collection<AccidentType> findAll();
}
