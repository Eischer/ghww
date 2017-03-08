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

}
