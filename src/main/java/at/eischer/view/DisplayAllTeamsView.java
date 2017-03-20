package at.eischer.view;

import at.eischer.model.Team;
import at.eischer.services.TeamService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class DisplayAllTeamsView {

    private List<Team> allTeams;

    @Inject
    TeamService teamService;

    public byte[] getLogo(int teamId) {
        Team team = teamService.findTeamById(teamId);
        return team.getLogo();
    }

    @PostConstruct
    public void init() {
        allTeams = teamService.findAllteams();
    }


    /*******************************/
    /*** GETTER - SETTER Section ***/
    /*******************************/

    public List<Team> getAllTeams() {
        return allTeams;
    }

    public void setAllTeams(List<Team> allTeams) {
        this.allTeams = allTeams;
    }

}
