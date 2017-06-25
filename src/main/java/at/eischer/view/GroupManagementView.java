package at.eischer.view;

import at.eischer.model.Team;
import at.eischer.services.SpielService;
import at.eischer.services.TeamService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class GroupManagementView {

    List<Team> allTeams;

    @Inject
    TeamService teamService;

    @Inject
    SpielService spielService;

    @PostConstruct
    public void init() {
        allTeams = teamService.findAllteams();
    }

    public boolean participateOnSpiel(Team team) {
        return spielService.participateOnSpiel(team);
    }

    public List<Team> getAllTeams() {
        return allTeams;
    }

    public void setAllTeams(List<Team> allTeams) {
        this.allTeams = allTeams;
    }

    public void save() {
        allTeams.forEach(t -> teamService.update(t));
    }
}
