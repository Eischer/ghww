package at.eischer.services;

import at.eischer.model.SeiderlHistory;
import at.eischer.model.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class SeiderlHistoryService {

    @PersistenceContext
    private EntityManager entityManager;

    public void insertSeiderlHistory(SeiderlHistory seiderlHistory) {
        entityManager.persist(seiderlHistory);
    }

    public List<SeiderlHistory> getSeiderHistoryByTeam(Team team) {
        TypedQuery<SeiderlHistory> getHistoryQuery = entityManager.createNamedQuery("SeiderlHistory.getHistory", SeiderlHistory.class).setParameter("team", team);
        return getHistoryQuery.getResultList();
    }
}