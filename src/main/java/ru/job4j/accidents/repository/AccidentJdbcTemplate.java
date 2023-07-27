package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@ThreadSafe
@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {
    private final JdbcTemplate jdbc;

    @Override
    public Optional<Accident> findById(int id) {
        Accident accident = jdbc.queryForObject(""" 
                                select accidents.id aId, accidents.name aName, accidents.text aText, 
                                accidents.address aAddress, 
                                types.id tId, types.name tName 
                                from accidents
                                join types on accidents.type_id = types.id
                                where accidents.id = ?
                                """,
                getAccidentWithType(), id);
        accident.setRules(new HashSet<>(jdbc.query(""" 
                                select rules.id rId, rules.name rName
                                from accidents
                                join accident_rule on accident_rule.accisent_id = accidents.id
                                join rules on rules.id = accident_rule.rule_id
                                where accidents.id = ?
                                """,
                    getRules(), accident.getId())));
        return Optional.ofNullable(accident);
    }

    @Override
    public Accident save(Accident accident) {
        return null;
    }

    public Accident save(Accident accident, Set<Rule> rules) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement("insert into accidents (name, text, address, type_id) values (?, ?, ?, ?)",
                                        new String[] {"id"});
                        ps.setString(1, accident.getName());
                        ps.setString(2, accident.getText());
                        ps.setString(3, accident.getAddress());
                        ps.setInt(4, accident.getType().getId());
                        return ps;
                    }
                },
                keyHolder);
        var id = keyHolder.getKey();
        for (var rule : rules) {
        jdbc.update("insert into accident_rule (accisent_id, rule_id) values (?, ?)",
                id,
                rule.getId());
        }
        return accident;
    }

    @Override
    public boolean update(Accident accident) {
        return false;
    }

    @Override
    public boolean update(Accident accident, Set<Rule> rules) {
        jdbc.update("delete from accident_rule where accisent_id = ?", accident.getId());
        for (var rule : rules) {
            jdbc.update("insert into accident_rule (accisent_id, rule_id) values (?, ?)",
                    accident.getId(),
                    rule.getId());
        }
       return jdbc.update("update accidents set name = ?, text = ?, address = ?, type_id = ? where id = ?",
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId(),
                accident.getId()) != 0;
    }

    @Override
    public boolean delete(int id) {
        jdbc.update("delete from accident_rule where accisent_id = ?", id);
        return jdbc.update("delete from accidents where id = ?", id) != 0;
    }

    @Override
    public Collection<Accident> findAll() {
        List<Accident> accidentList = jdbc.query(""" 
                                select accidents.id aId, accidents.name aName, accidents.text aText, 
                                accidents.address aAddress, 
                                types.id tId, types.name tName 
                                from accidents
                                join types on accidents.type_id = types.id
                                """,
                getAccidentWithType());
        for (var accident : accidentList) {
            accident.setRules(new HashSet<>(jdbc.query(""" 
                                select rules.id rId, rules.name rName
                                from accidents
                                join accident_rule on accident_rule.accisent_id = accidents.id
                                join rules on rules.id = accident_rule.rule_id
                                where accidents.id =?
                                """,
                    getRules(), accident.getId())));
        }
        return accidentList;
    }

    private RowMapper<Accident> getAccidentWithType() {
       return new RowMapper<Accident>() {
            @Override
            public Accident mapRow(ResultSet rs, int rowNum) throws SQLException {
                Accident accident = new Accident();
                accident.setId(rs.getInt("aId"));
                accident.setName(rs.getString("aName"));
                accident.setText(rs.getString("aText"));
                accident.setAddress(rs.getString("aAddress"));
                accident.setType(new AccidentType(rs.getInt("tId"),
                        rs.getString("tName")));
                return accident;
            }
        };
    }

    private RowMapper<Rule> getRules() {
        return new RowMapper<Rule>() {
            @Override
            public Rule mapRow(ResultSet rs, int rowNum) throws SQLException {
                Rule rule = new Rule();
                rule.setId(rs.getInt("rId"));
                rule.setName(rs.getString("rName"));
                return rule;
            }
        };
    }
}