package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

    private final SessionFactory sf;

    @Override
    public Set<Rule> findById(String[] id) {
        Session session = sf.openSession();
        List<Rule> result = new ArrayList<>();
        List<Integer> ids = Arrays.stream(id).map(x -> Integer.parseInt(x)).collect(Collectors.toList());
        try {
            session.beginTransaction();
            result = session.createQuery("from Rule r where r.id in :fId", Rule.class)
                    .setParameter("fId", ids)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return new HashSet<>(result);
    }

    @Override
    public Rule save(Rule rule) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(rule);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rule;
    }

    @Override
    public Collection<Rule> findAll() {
        Session session = sf.openSession();
        List<Rule> result = new ArrayList<>();
        try {
            session.beginTransaction();
            result = session.createQuery("from Rule ORDER BY id").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }
}
