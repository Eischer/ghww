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
public class GroupManagementView {

    List<Team> allTeams;

    @Inject
    TeamService teamService;

    @PostConstruct
    public void init() {
        allTeams = teamService.findAllteams();
    }

    public List<Team> getAllTeams() {
        return allTeams;
    }

    public void setAllTeams(List<Team> allTeams) {
        this.allTeams = allTeams;
    }
}
