package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import java.util.*;

@Repository
@AllArgsConstructor
@Primary
@ThreadSafe
public class AccidentHibernate implements AccidentRepository {
    private final CrudRepository crudRepository;

    @Override
    public Collection<Accident> findAll() {
        return crudRepository.query("""
                    select distinct a from Accident a
                    left join a.type t
                    left join fetch a.rules r
                    order by a.id
                    """,
                Accident.class);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return crudRepository.optional(Accident.class, id);
    }

    @Override
    public Accident save(Accident accident) {
        crudRepository.run(session -> session.save(accident));
        return accident;
        }

    @Override
    public Accident save(Accident accident, Set<Rule> rules) {
        return save(accident);
    }

    @Override
    public boolean update(Accident accident) {
        return crudRepository.run(session -> session.merge(accident));
    }

    @Override
    public boolean update(Accident accident, Set<Rule> rules) {
        return update(accident);
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.run("delete Accident where id = :fId",
                Map.of("fId", id));
    }
}
