package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Repository
@Primary
@ThreadSafe
@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {
    private final JdbcTemplate jdbc;
    private final AccidentTypeRepository accidentTypeRepository;
    private final RuleRepository ruleRepository;

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(
                jdbc.queryForObject("select id, name, text, address, type, rules from accidents where id = ?",
                        getAccident(), id));
    }

    public Accident save(Accident accident) {
        System.out.println(accident.getRules().stream().map(Rule::getId).toArray());
        jdbc.update("insert into accidents (name, text, address, type, rules) values (?, ?, ?, ?, ?)",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getRules().stream().mapToInt(Rule::getId).toArray());
        return accident;
    }

    @Override
    public boolean update(Accident accident) {
        return jdbc.update(
                "update accidents set name = ?, text = ?, address = ?, type = ?, rules = ? where id = ?",
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId(),
                accident.getRules().stream().mapToInt(Rule::getId).toArray(), accident.getId()) != 0;
    }

    @Override
    public boolean delete(int id) {
        return jdbc.update("delete from accidents where id = ?", id) != 0;
    }

    public Collection<Accident> findAll() {
        return jdbc.query("select id, name, text, address, type, rules from accidents",
                getAccident());
    }

    private RowMapper<Accident> getAccident() {
       return new RowMapper<Accident>() {
            @Override
            public Accident mapRow(ResultSet rs, int rowNum) throws SQLException {
                Accident accident = new Accident();
                accident.setId(rs.getInt("id"));
                accident.setName(rs.getString("name"));
                accident.setText(rs.getString("text"));
                accident.setAddress(rs.getString("address"));
                accident.setType(accidentTypeRepository.findById(rs.getInt("type")).get());
                String[] array = Arrays.stream((Object[]) rs.getArray("rules").getArray())
                        .map(String::valueOf)
                        .toArray(String[]::new);
                accident.setRules(ruleRepository.findById(array));
                return accident;
            }
        };
    }
}