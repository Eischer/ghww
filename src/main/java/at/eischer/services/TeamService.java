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
        return entityManager.find(Team.class, id);
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

    public int getMaxCountOfSeiderl() {
        Query maxSeiderQuery = entityManager.createNamedQuery("Team.getMaxSeiderlCount");
        return (int) maxSeiderQuery.getSingleResult();
    }

    public void removeTeam(int teamId) {
        Team teamToRemove =  entityManager.find(Team.class, teamId);
        entityManager.remove(teamToRemove);
    }

    public List<Team> findAllteamsOrderBySeiderl() {
        TypedQuery<Team> allTeams = entityManager.createNamedQuery("Team.findAllOrderBySeiderl", Team.class);
        return allTeams.getResultList();
    }
}
