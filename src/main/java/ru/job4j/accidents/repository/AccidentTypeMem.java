package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/*@Repository
@ThreadSafe*/
public class AccidentTypeMem implements AccidentTypeRepository {

    private final Map<Integer, AccidentType> accidentTypes = new ConcurrentHashMap<>();

    public AccidentTypeMem() {
        save(new AccidentType(1, "Две машины"));
        save(new AccidentType(2, "Машина и человек"));
        save(new AccidentType(3, "Машина и велосипед"));
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(accidentTypes.get(id));
    }

    @Override
    public AccidentType save(AccidentType accidentType) {
        return accidentTypes.put(accidentType.getId(), accidentType);
    }

    @Override
    public Collection<AccidentType> findAll() {
        return accidentTypes.values();
    }
}
