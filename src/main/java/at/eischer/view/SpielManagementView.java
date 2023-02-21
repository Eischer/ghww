package at.eischer.view;

import at.eischer.model.Spiel;
import at.eischer.model.Team;
import at.eischer.services.RankCalculator;
import at.eischer.services.SpielService;
import at.eischer.services.TeamService;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Named
@ViewScoped
public class SpielManagementView implements Serializable {

    @Inject
    private SpielService spielService;

    @Inject
    private TeamService teamService;

    @Inject
    private RankCalculator rankCalculator;

    private TeamRank[] result;

    private String gruppe;

    private List<Team> teamPerGruppe;

    private List<Spiel> spielePerGruppe;

    private SpielInput spielInput;

    @PostConstruct
    public void init() {
        spielInput = new SpielInput();
        this.gruppe = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("currentGruppe");
        if (this.gruppe == null) {
            this.gruppe = "A";
        }
        this.teamPerGruppe = teamService.findTeamsForGruppe(this.gruppe);
        this.spielePerGruppe = spielService.getAllSpielePerGruppe(this.gruppe);
        this.result = new TeamRank[teamPerGruppe.size()];
        this.result = rankCalculator.calculateStandings(this.gruppe, this.result);
    }



    public void saveGame() {
        LocalTime zeit = LocalTime.of(spielInput.getHour(), spielInput.getMinute());
        spielService.save(new Spiel(zeit, this.gruppe, spielInput.getHomeTeam(), spielInput.getAwayTeam()));
        this.spielePerGruppe = spielService.getAllSpielePerGruppe(this.gruppe);
    }

    public void deleteSpiel(Spiel spiel) {
        spielService.remove(spiel);
        this.spielePerGruppe = spielService.getAllSpielePerGruppe(this.gruppe);
        this.result = new TeamRank[teamPerGruppe.size()];
        this.result = rankCalculator.calculateStandings(this.gruppe, this.result);
    }

    public void saveResult(Spiel spiel) {
        spielService.update(spiel);
        this.result = new TeamRank[teamPerGruppe.size()];
        this.result = rankCalculator.calculateStandings(this.gruppe, this.result);
    }

    public void deleteResult(Spiel spiel) {
        spiel.setToreHomeTeam(null);
        spiel.setToreAwayTeam(null);
        spielService.update(spiel);
        this.result = new TeamRank[teamPerGruppe.size()];
        this.result = rankCalculator.calculateStandings(this.gruppe, this.result);
    }

    public Team getTeamById(Long teamId) {
        if (teamId == null) {
            throw new IllegalArgumentException("no id");
        } else {
            for (Team team : teamPerGruppe) {
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

    public TeamRank[] getResult() {
        return result;
    }

    public void setResult(TeamRank[] result) {
        this.result = result;
    }
}
