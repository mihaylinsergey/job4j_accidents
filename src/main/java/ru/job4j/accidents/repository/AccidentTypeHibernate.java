package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Primary
@ThreadSafe
public class AccidentTypeHibernate implements AccidentTypeRepository {

    private final SessionFactory sf;

    @Override
    public Optional<AccidentType> findById(int id) {
        Session session = sf.openSession();
        Optional<AccidentType> result = Optional.empty();
        try {
            session.beginTransaction();
            result = Optional.of(session.createQuery("""
                    from AccidentType i 
                    where i.id = :fId
                    """, AccidentType.class)
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
    public AccidentType save(AccidentType accidentType) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(accidentType);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return accidentType;
    }

    @Override
    public Collection<AccidentType> findAll() {
        Session session = sf.openSession();
        List<AccidentType> result = new ArrayList<>();
        try {
            session.beginTransaction();
            result = session.createQuery("from AccidentType ORDER BY id").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }
}
