package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
@ThreadSafe
@AllArgsConstructor
public class RuleJdbc implements RuleRepository {

    private final JdbcTemplate jdbc;

    @Override
    public Set<Rule> findById(String[] id) {
        Set<Rule> rules = new HashSet<>();
        if (id != null) {
            for (String i : id) {
                rules.add(jdbc.queryForObject("select id, name from rules where id = ?",
                        (rs, rowNum) -> {
                            Rule rule = new Rule();
                            rule.setId(rs.getInt("id"));
                            rule.setName(rs.getString("name"));
                            return rule;
                        },
                            Integer.parseInt(i)));
            }
        }
        return rules;
    }

    @Override
    public Rule save(Rule rule) {
        jdbc.update("insert into rules (name) values (?)",
                rule.getName());
        return null;
    }

    @Override
    public Collection<Rule> findAll() {
        return jdbc.query("select id, name from rules",
                (rs, rowNum) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }
}
