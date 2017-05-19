package at.eischer.services;

import at.eischer.model.SeiderlHistory;
import at.eischer.model.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

public class SeiderlHistoryService {

    @PersistenceContext
    private EntityManager entityManager;

    public void insertSeiderlHistory(SeiderlHistory seiderlHistory) {
        entityManager.persist(seiderlHistory);
    }

    public List<SeiderlHistory> getSeiderHistoryByTeam(Team team) {
        LocalDateTime yesterday = LocalDateTime.now().minusHours(12);

        TypedQuery<SeiderlHistory> getHistoryQuery = entityManager.createNamedQuery("SeiderlHistory.getHistory", SeiderlHistory.class).
                setParameter("team", team).
                setParameter("currentTime", yesterday);
        return getHistoryQuery.getResultList();
    }

    public void deleteHistory() {
        entityManager.createNamedQuery("SeiderlHistory.deleteWholeHistory").executeUpdate();
    }
}