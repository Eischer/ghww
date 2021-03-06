package at.eischer.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class Repository {

    @PersistenceContext(unitName = "myPersistenceUnit")
    protected EntityManager entityManager;

    public <T> void save(T object) {
        entityManager.persist(object);
    }

    public <T> void update(T object) {
        entityManager.merge(object);
    }

    public <T> void remove(T object) {
        entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
    }

}
