package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class AccidentMem implements AccidentRepository {

   private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

   private AtomicInteger nexId = new AtomicInteger(0);

   public AccidentMem() {
        save(new Accident(0, "name1", "text1", "address1"));
        save(new Accident(0, "name2", "text2", "address2"));
        save(new Accident(0, "name3", "text3", "address3"));
        save(new Accident(0, "name4", "text4", "address4"));
        save(new Accident(0, "name5", "text5", "address5"));
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
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(), (id, oldAccident) -> {
            return new Accident(oldAccident.getId(), accident.getName(), accident.getText(), accident.getAddress());
        }) != null;
    }

    @Override
    public boolean delete(int id) {
        return accidents.remove(id) != null;
    }
}
