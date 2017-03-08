package at.eischer.services;

import at.eischer.dao.Repository;
import at.eischer.model.Team;

import javax.ejb.Stateless;
import javax.persistence.NamedQuery;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
@NamedQuery(name = "allTeams", query = "SELECT t FROM Team t")
public class TeamService extends Repository{

    public List<Team> findAllteams () {
//        TypedQuery<Team> allTeams = entityManager.createNamedQuery("allTeams", Team.class);
//        return allTeams.getResultList();
        Team team = new Team();
        team.setId(1);
        team.setName("Muh");
        List<Team> teams = new ArrayList<>();
        teams.add(team);
        return teams;
    }
}
