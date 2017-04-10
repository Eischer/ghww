package at.eischer.services;

import at.eischer.model.SeiderlHistory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class SeiderHistoryService {

    @PersistenceContext
    private EntityManager entityManager;

    public void insertSeiderlHistory(SeiderlHistory seiderlHistory) {
        entityManager.persist(seiderlHistory);
    }

    public void getSeiderHistoryByTeam() {
        Query getHistoryQuery = entityManager.createNamedQuery("SeiderlHistory.getHistoryFor");
        getHistoryQuery.getResultList();
    }
}
