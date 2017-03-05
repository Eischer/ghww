package at.eischer.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class Repository {

    @PersistenceContext(unitName = "myPersistenceUnit")
    private EntityManager entityManager;

    public <T> void save(T object) {
        entityManager.persist(object);
    }
}
