package at.eischer.view;

import at.eischer.model.FinalSpiel;
import at.eischer.model.Team;
import at.eischer.services.SpielService;
import at.eischer.services.TeamService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@RequestScoped
public class FinalPhaseView implements Serializable {

    private String gruppe;

    private List<FinalSpiel> allFinalSpiele;

    private List<Team> allTeams;

    private SpielInput spielInput;

    @Inject
    TeamService teamService;

    @Inject
    private SpielService spielService;

    @PostConstruct
    public void init() {
        spielInput = new SpielInput();
        this.allFinalSpiele = spielService.getAllFinalSpiele();
        this.allTeams = teamService.findAllteams();
    }

    public void saveResult(FinalSpiel finalSpiel) {
        spielService.update(finalSpiel);
    }

    public void deleteResult(FinalSpiel finalSpiel) {
        finalSpiel.setToreHomeTeam(null);
        finalSpiel.setToreAwayTeam(null);
        spielService.update(finalSpiel);
    }

    public Team getTeamById(Long teamId) {
        if (teamId == null) {
            throw new IllegalArgumentException("no id");
        } else {
            for (Team team : this.allTeams) {
                if (teamId.equals(team.getId())) {
                    return team;
                }
            }
            return null;
        }
    }

    public SpielInput getSpielInput() {
        return this.spielInput;
    }

    public String getGruppe() {
        return this.gruppe;
    }

    public void setGruppe(String gruppe) {
        this.gruppe = gruppe;
    }

    public List<FinalSpiel> getAllFinalSpiele() {
        return this.allFinalSpiele;
    }

    public void setAllFinalSpiele(List<FinalSpiel> allFinalSpiele) {
        this.allFinalSpiele = allFinalSpiele;
    }

    public List<Team> getAllTeams() {
        return allTeams;
    }

    public void setAllTeams(List<Team> allTeams) {
        this.allTeams = allTeams;
    }
}
