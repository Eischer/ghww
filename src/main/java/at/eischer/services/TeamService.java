package at.eischer.services;

import at.eischer.dao.Repository;
import at.eischer.model.Team;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Stateless
public class TeamService extends Repository implements Serializable{

    public List<Team> findAllteams () {
        TypedQuery<Team> allTeams = entityManager.createNamedQuery("Team.findAll", Team.class);
        return allTeams.getResultList();
    }

    public Team findTeamById(long id) {
        return entityManager.find(Team.class, id);
    }

    public void incrementSeiderl(long id, float seidlCount) {
        Query incrementSeiderlQuery = entityManager.createNamedQuery("Team.incrementSeidlCount")
                .setParameter("teamId", id)
                .setParameter("seidlCount", seidlCount);
        incrementSeiderlQuery.executeUpdate();
    }

    public void decrementSeiderl(long id, float seidlCount) {
        Query decrementSeiderlQuery = entityManager.createNamedQuery("Team.decrementSeidlCount")
                .setParameter("teamId", id)
                .setParameter("seidlCount", seidlCount);
        decrementSeiderlQuery.executeUpdate();
    }

    public float getMaxCountOfSeiderl() {
        Query maxSeiderQuery = entityManager.createNamedQuery("Team.getMaxSeiderlCount");
        return (float) maxSeiderQuery.getSingleResult();
    }

    public void removeTeam(Team teamToRemove) {
        entityManager.remove(entityManager.contains(teamToRemove) ? teamToRemove : entityManager.merge(teamToRemove));
    }

    public List<Team> findAllteamsOrderBySeiderl() {
        TypedQuery<Team> allTeams = entityManager.createNamedQuery("Team.findAllOrderBySeiderl", Team.class);
        return allTeams.getResultList();
    }

    public void resetSeidlCounter() {
        List<Team> allTeams = findAllteams();
        allTeams.forEach(t -> t.setSeiderlCounter(0));
    }
}
