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
public class SpielManagementView {

    private String gruppe;

    private List<Team> teamPerGruppe;

    private Team homeTeam;

    private Team awayTeam;

    @Inject
    private TeamService teamService;

    @PostConstruct
    public void init() {
        if (this.gruppe == null) {
            this.gruppe = "A";
        }
        this.teamPerGruppe = teamService.findTeamsForGruppe(gruppe);
    }

    public void setTeamsDependingOnGroup (String gruppe) {
        this.gruppe = gruppe;
        this.teamPerGruppe = teamService.findTeamsForGruppe(gruppe);
    }

    public String saveGame() {
        System.out.println("Hallo");
        return "/spielManagement?faces-redirect=true";

    }

    public Team getTeamById(Long teamId, boolean isHomeTeam) {
        if (teamId == null) {
            throw new IllegalArgumentException("no id");
        } else {
            for(Team team : teamPerGruppe) {
                if (teamId.equals(team.getId())) {
                    return team;
                }
            }
            return null;
        }
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

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }
}
