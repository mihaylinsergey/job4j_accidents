package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Primary
@ThreadSafe
public class AccidentTypeHibernate implements AccidentTypeRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<AccidentType> findById(int id) {
        return crudRepository.optional(AccidentType.class, id);
    }

    @Override
    public AccidentType save(AccidentType accidentType) {
        crudRepository.run(session -> session.save(accidentType));
        return accidentType;
    }

    @Override
    public Collection<AccidentType> findAll() {
        return crudRepository.query("from AccidentType ORDER BY id",
                AccidentType.class);
    }
}
