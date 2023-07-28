package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
@Primary
@ThreadSafe
public class RuleHibernate implements RuleRepository {

    private final CrudRepository crudRepository;

    @Override
    public Set<Rule> findById(String[] id) {
        List<Integer> ids = new ArrayList<>();
        if (id != null) {
            ids = Arrays.stream(id).map(x -> Integer.parseInt(x)).collect(Collectors.toList());
        }
        return new HashSet<>(crudRepository.query(
                "from Rule r where r.id in :fId", Rule.class, Map.of("fId", ids)));
    }

    @Override
    public Rule save(Rule rule) {
        crudRepository.run(session -> session.save(rule));
        return rule;
    }

    @Override
    public Collection<Rule> findAll() {
        return crudRepository.query("from Rule ORDER BY id", Rule.class);
    }
}
