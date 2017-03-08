package at.eischer.services;

import at.eischer.dao.Repository;
import at.eischer.model.Team;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class TeamService extends Repository{

    public List<Team> findAllteams () {
        TypedQuery<Team> allTeams = entityManager.createNamedQuery("Team.finadAll", Team.class);
        return allTeams.getResultList();
    }
}
