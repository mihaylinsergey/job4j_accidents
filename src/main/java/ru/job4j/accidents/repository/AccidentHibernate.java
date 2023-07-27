package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    private final SessionFactory sf;

    @Override
    public Collection<Accident> findAll() {
        Session session = sf.openSession();
        List<Accident> result = new ArrayList<>();
        try {
            session.beginTransaction();
            result = session.createQuery("""
                    select distinct a from Accident a
                    left join a.type t
                    left join fetch a.rules r
                    order by a.id
                    """, Accident.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<Accident> findById(int id) {
        Session session = sf.openSession();
        Optional<Accident> result = Optional.empty();
        try {
            session.beginTransaction();
            result = Optional.of(session.createQuery("""
                    select distinct a from Accident a
                    left join a.type t
                    left join fetch a.rules r
                    where a.id = :fId
                    """, Accident.class)
                    .setParameter("fId", id)
                    .uniqueResult());
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Accident save(Accident accident) {
        return null;
        }

    @Override
    public Accident save(Accident accident, Set<Rule> rules) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(accident);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return accident;
    }

    @Override
    public boolean update(Accident accident) {
        return false;
    }

    @Override
    public boolean update(Accident accident, Set<Rule> rules) {
        boolean rsl = false;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.merge(accident);
            session.getTransaction().commit();
            rsl = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public boolean delete(int id) {
        boolean rsl = false;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "delete Accident where id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            rsl = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }
}
