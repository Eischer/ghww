package at.eischer.view;

import at.eischer.model.Spiel;
import at.eischer.model.Team;
import at.eischer.services.SpielService;
import at.eischer.services.TeamService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class SpielManagementView {

    private String gruppe;

    private List<Team> teamPerGruppe;

    private List<Spiel> spielePerGruppe;

    private SpielInput spielInput;

    private List<TeamRank> standings;

    @Inject
    private SpielService spielService;

    @Inject
    private TeamService teamService;

    @PostConstruct
    public void init() {
        spielInput = new SpielInput();
        this.gruppe = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("currentGruppe");
        if (this.gruppe == null) {
            this.gruppe = "A";
        }
        this.teamPerGruppe = teamService.findTeamsForGruppe(this.gruppe);
        this.spielePerGruppe = spielService.getAllSpielePerGruppe(this.gruppe);
        this.standings = calculateStandings();
    }

    private List<TeamRank> calculateStandings() {
        List<Team> allTeamsForGroup = teamService.findTeamsForGruppe(this.gruppe);
        Map<Long, TeamRank> standingsAsMap = new HashMap<>();
        for (Team team : allTeamsForGroup) {
            standingsAsMap.put(team.getId(), new TeamRank(team));
        }

        List<Spiel> allSpieleForGroup = spielService.getAllSpielePerGruppe(this.gruppe);
        for (Spiel spiel : allSpieleForGroup) {
            if (spiel.getToreHomeTeam() != null && spiel.getToreAwayTeam() != null) {
                TeamRank teamRankForHomeTeam = standingsAsMap.get(spiel.getHomeTeam().getId());
                teamRankForHomeTeam = addResultToTeamRank(teamRankForHomeTeam, spiel.getToreHomeTeam(), spiel.getToreAwayTeam());
                standingsAsMap.put(spiel.getHomeTeam().getId(), teamRankForHomeTeam);

                TeamRank teamRankForAwayTeam = standingsAsMap.get(spiel.getAwayTeam().getId());
                teamRankForAwayTeam = addResultToTeamRank(teamRankForAwayTeam, spiel.getToreAwayTeam(), spiel.getToreHomeTeam());
                standingsAsMap.put(spiel.getAwayTeam().getId(), teamRankForAwayTeam);
            }
        }
        return new ArrayList<>(standingsAsMap.values());
    }

    private TeamRank addResultToTeamRank(TeamRank teamRank, int ownTore, int foreignTore) {
        teamRank.plusGoals += ownTore;
        teamRank.minusGoals += foreignTore;
        if (ownTore > foreignTore) {
            teamRank.points += 3;
        } else if (ownTore == foreignTore) {
            teamRank.points++;
        }
        return teamRank;
    }

    public void setTeamsDependingOnGroup (String gruppe) {
        this.gruppe = gruppe;
        this.teamPerGruppe = teamService.findTeamsForGruppe(this.gruppe);
        this.spielePerGruppe = spielService.getAllSpielePerGruppe(this.gruppe);
    }

    public String saveGame() {
        LocalTime zeit = LocalTime.of(spielInput.getHour(), spielInput.getMinute());
        spielService.save(new Spiel(zeit, this.gruppe, spielInput.getHomeTeam(), spielInput.getAwayTeam()));
        return "/spielManagement?faces-redirect=true";

    }

    public Object saveResult(Spiel spiel) {
        spielService.update(spiel);
        return "/spielManagement?faces-redirect=true";
    }

    public Team getTeamById(Long teamId) {
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

    public SpielInput getSpielInput() {
        return spielInput;
    }

    public String getGruppe() {
        return gruppe;
    }

    public void setGruppe(String gruppe) {
        this.gruppe = gruppe;
    }

    public List<Team> getTeamPerGruppe() {
        return teamPerGruppe;
    }

    public void setTeamPerGruppe(List<Team> teamPerGruppe) {
        this.teamPerGruppe = teamPerGruppe;
    }

    public List<Spiel> getSpielePerGruppe() {
        return spielePerGruppe;
    }

    public void setSpielePerGruppe(List<Spiel> spielePerGruppe) {
        this.spielePerGruppe = spielePerGruppe;
    }

    public List<TeamRank> getStandings() {
        return standings;
    }

    public void setStandings(List<TeamRank> standings) {
        this.standings = standings;
    }
}
