package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeMem;
import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeServiceMem implements AccidentTypeService {

    private final AccidentTypeMem accidentTypeMem;

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeMem.findById(id);
    }

    @Override
    public AccidentType save(AccidentType accidentType) {
        return accidentTypeMem.save(accidentType);
    }

    @Override
    public Collection<AccidentType> findAll() {
        return accidentTypeMem.findAll();
    }
}
