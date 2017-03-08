package at.eischer.view;

import at.eischer.model.Team;
import at.eischer.services.TeamService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class TeamView {

    private Team team;

    private List<Team> allTeams;

    @Inject
    TeamService teamService;

    public String saveTeam () {
        teamService.save(team);
        allTeams = teamService.findAllteams();
        return "maintainTeams";
    }

    @PostConstruct
    public void init () {
        team = new Team();
    }


    /*******************************/
    /*** GETTER - SETTER Section ***/
    /*******************************/

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Team> getAllTeams() {
        return allTeams;
    }

    public void setAllTeams(List<Team> allTeams) {
        this.allTeams = allTeams;
    }
}
