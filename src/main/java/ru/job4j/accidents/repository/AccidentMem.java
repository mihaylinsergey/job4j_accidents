package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/*@Repository
@ThreadSafe*/
public class AccidentMem implements AccidentRepository {

   private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

   private AtomicInteger nexId = new AtomicInteger(0);

   public AccidentMem() {
        save(new Accident(0, "name1", "text1", "address1", new AccidentType(1, "Две машины"),
                Set.of(new Rule(1, "Статья. 1"),
                new Rule(2, "Статья. 2"),
                new Rule(3, "Статья. 3"))));
        save(new Accident(0, "name2", "text2", "address2", new AccidentType(2, "Машина и человек"),
                Set.of(new Rule(2, "Статья. 2"),
                        new Rule(3, "Статья. 3"))));
        save(new Accident(0, "name3", "text3", "address3", new AccidentType(3, "Машина и велосипед"),
                Set.of(new Rule(1, "Статья. 1"),
                        new Rule(3, "Статья. 3"))));
        save(new Accident(0, "name4", "text4", "address4", new AccidentType(1, "Две машины"),
                Set.of(new Rule(1, "Статья. 1"),
                        new Rule(2, "Статья. 2"))));
        save(new Accident(0, "name5", "text5", "address5", new AccidentType(2, "Машина и человек"),
                Set.of(new Rule(1, "Статья. 1"),
                        new Rule(2, "Статья. 2"),
                        new Rule(3, "Статья. 3"))));
   }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    @Override
    public Accident save(Accident accident) {
        accident.setId(nexId.incrementAndGet());
        accidents.put(accident.getId(), accident);
        return accident;
    }

    @Override
    public Accident save(Accident accident, Set<Rule> rules) {
        return null;
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(), (id, oldAccident) -> {
            return new Accident(oldAccident.getId(),
                    accident.getName(),
                    accident.getText(),
                    accident.getAddress(),
                    accident.getType(),
                    accident.getRules());
        }) != null;
    }

    @Override
    public boolean update(Accident accident, Set<Rule> rules) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return accidents.remove(id) != null;
    }
}
