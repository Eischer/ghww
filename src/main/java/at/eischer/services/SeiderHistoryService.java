package at.eischer.services;

import at.eischer.model.SeiderlHistory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class SeiderHistoryService {

    @PersistenceContext
    private EntityManager entityManager;

    public void insertSeiderlHistory(SeiderlHistory seiderlHistory) {
        entityManager.persist(seiderlHistory);
    }
}
