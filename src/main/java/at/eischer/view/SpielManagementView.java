package at.eischer.view;

import at.eischer.model.Team;
import at.eischer.services.TeamService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class SpielManagementView {

    private String gruppe;

    private List<Team> teamPerGruppe;

    @Inject
    TeamService teamService;

    public void setTeamsDependingOnGroup (String gruppe) {
        teamPerGruppe = teamService.findTeamsForGruppe(gruppe);
    }

    public String getGruppe() {
        return gruppe;
    }

    public void setGruppe(String gruppe) {
        this.gruppe = gruppe;
    }

    public void setTeamsDepending(String gruppe) {
        this.gruppe = gruppe;
    }

    public List<Team> getTeamPerGruppe() {
        return teamPerGruppe;
    }

    public void setTeamPerGruppe(List<Team> teamPerGruppe) {
        this.teamPerGruppe = teamPerGruppe;
    }
}
