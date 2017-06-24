package at.eischer.view;

import at.eischer.model.FinalSpiel;
import at.eischer.model.Spiel;
import at.eischer.model.Team;
import at.eischer.services.SpielService;
import at.eischer.services.TeamService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.*;

@Named
@ViewScoped
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

    public void saveGame() {
        LocalTime zeit = LocalTime.of(spielInput.getHour(), spielInput.getMinute());
        spielService.save(new Spiel(zeit, this.gruppe, spielInput.getHomeTeam(), spielInput.getAwayTeam()));
        this.allFinalSpiele = spielService.getAllFinalSpiele();
    }

    public void deleteSpiel(Spiel spiel) {
        spielService.removeSpiel(spiel);
        this.allFinalSpiele = spielService.getAllFinalSpiele();
    }

    public void saveResult(Spiel spiel) {
        spielService.update(spiel);
    }

    public void deleteResult(Spiel spiel) {
        spiel.setToreHomeTeam(null);
        spiel.setToreAwayTeam(null);
        spielService.update(spiel);
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
