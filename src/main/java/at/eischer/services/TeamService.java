package at.eischer.services;

import at.eischer.dao.Repository;
import at.eischer.model.Team;

import javax.ejb.Stateless;
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
}
