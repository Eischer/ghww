package at.eischer.services;

import at.eischer.dao.Repository;
import at.eischer.model.Team;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class TeamService extends Repository{

    public List<Team> findAllteams () {
        TypedQuery<Team> allTeams = entityManager.createNamedQuery("Team.findAll", Team.class);
        return allTeams.getResultList();
    }

    public Team findTeamById(int id) {
        TypedQuery<Team> queryTeamById = entityManager.createNamedQuery("Team.findById", Team.class);
        queryTeamById.setParameter("teamId", id);
        return queryTeamById.getSingleResult();
    }

    public void incrementSeiderl(int id) {
        Query incrementSeiderlQuery = entityManager.createQuery("UPDATE Team t SET t.seiderlCounter=t.seiderlCounter + 1 WHERE t.id = :teamId");
        incrementSeiderlQuery.setParameter("teamId", id);
        incrementSeiderlQuery.executeUpdate();
    }

    public void decrementSeiderl(int id) {
        Query decrementSeiderlQuery = entityManager.createQuery("UPDATE Team t SET t.seiderlCounter=t.seiderlCounter - 1 WHERE t.id = :teamId");
        decrementSeiderlQuery.setParameter("teamId", id);
        decrementSeiderlQuery.executeUpdate();
    }
}
