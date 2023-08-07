package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.Collection;
import java.util.Optional;

/*@Service
@ThreadSafe*/
@AllArgsConstructor
public class AccidentTypeServiceHibernate implements AccidentTypeService {

    private final AccidentTypeRepository accidentTypeRepository;

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }

    @Override
    public AccidentType save(AccidentType accidentType) {
        return accidentTypeRepository.save(accidentType);
    }

    @Override
    public Collection<AccidentType> findAll() {
        return accidentTypeRepository.findAll();
    }
}
