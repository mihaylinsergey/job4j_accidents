package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import java.util.Collection;
import java.util.Optional;

@Repository
@ThreadSafe
@AllArgsConstructor
public class AccidentTypeJdbc implements AccidentTypeRepository {

    private final JdbcTemplate jdbc;

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(jdbc.queryForObject("select id, name from types where id = ?",
          (rs, rowNum) -> {
            AccidentType type = new AccidentType();
            type.setId(rs.getInt("id"));
            type.setName(rs.getString("name"));
            return type;
          },
                id));
    }

    @Override
    public AccidentType save(AccidentType accidentType) {
        jdbc.update("insert into types (name) values (?)",
                accidentType.getName());
        return accidentType;
    }

    @Override
    public Collection<AccidentType> findAll() {
        return jdbc.query("select id, name from types",
                (rs, rowNum) -> {
                    AccidentType type = new AccidentType();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                });
    }
}
