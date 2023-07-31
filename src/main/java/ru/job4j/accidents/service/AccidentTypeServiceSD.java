package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepositorySD;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
@Primary
public class AccidentTypeServiceSD {

    private final AccidentTypeRepositorySD accidentTypeRepositorySD;

    public Collection<AccidentType> findAll() {
        return accidentTypeRepositorySD.findAll();
    }

    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepositorySD.findById(id);
    }
}
