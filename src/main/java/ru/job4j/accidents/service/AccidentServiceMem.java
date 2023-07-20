package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
@ThreadSafe
public class AccidentServiceMem implements AccidentService {

    private final AccidentRepository accidentRepository;

    private final AccidentTypeService accidentTypeService;

    @Override
    public Collection<Accident> findAll() {
        return accidentRepository.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public Accident save(Accident accident) {
        Accident accidentWithType = getAccidentWithType(accident);
        return accidentRepository.save(accidentWithType);
    }

    @Override
    public boolean update(Accident accident) {
        Accident accidentWithType = getAccidentWithType(accident);
        return accidentRepository.update(accidentWithType);
    }

    @Override
    public boolean delete(int id) {
        return accidentRepository.delete(id);
    }

    private Accident getAccidentWithType(Accident accident) {
        int typeId = accident.getType().getId();
        var type = accidentTypeService.findById(typeId).get();
        accident.setType(type);
        return accident;
    }
}
