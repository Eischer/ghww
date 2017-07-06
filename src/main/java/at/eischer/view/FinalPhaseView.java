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
import java.time.LocalTime;
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

    private List<TeamRank> result;

    @PostConstruct
    public void init() {
        spielInput = new SpielInput();
        this.allFinalSpiele = spielService.getAllFinalSpiele();
        this.allTeams = teamService.findAllteams();
        this.result = new ArrayList<>();
    }

    public void saveFinalSpiel() {
        FinalSpiel finalSpiel = new FinalSpiel();
        finalSpiel.setIndicator(spielInput.getSpielDescription());
        finalSpiel.setZeit(LocalTime.of(spielInput.getHour(), spielInput.getMinute()));
        finalSpiel.setHomeTeam(spielInput.getHomeTeam());
        finalSpiel.setAwayTeam(spielInput.getAwayTeam());
        spielService.save(finalSpiel);
        this.allFinalSpiele = spielService.getAllFinalSpiele();
    }

    public void saveResult(FinalSpiel finalSpiel) {
        spielService.update(finalSpiel);
    }

    public void deleteResult(FinalSpiel finalSpiel) {
        finalSpiel.setToreHomeTeam(null);
        finalSpiel.setToreAwayTeam(null);
        spielService.update(finalSpiel);
    }

    public void deleteSpiel(FinalSpiel finalSpiel) {
        spielService.removeFianlSpiel(finalSpiel);
        this.allFinalSpiele = spielService.getAllFinalSpiele();
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
        if (this.allFinalSpiele.isEmpty()) {
            this.allFinalSpiele.add(new FinalSpiel("Kreuzspiel 1", LocalTime.of(14, 25)));
            this.allFinalSpiele.add(new FinalSpiel("Kreuzspiel 2", LocalTime.of(14, 25)));
            this.allFinalSpiele.add(new FinalSpiel("Spiel um Platz 11", LocalTime.of(14, 45)));
            this.allFinalSpiele.add(new FinalSpiel("Spiel um Platz 9", LocalTime.of(14, 45)));
            this.allFinalSpiele.add(new FinalSpiel("Spiel um Platz 7", LocalTime.of(15, 10)));
            this.allFinalSpiele.add(new FinalSpiel("Spiel um Platz 5", LocalTime.of(15, 10)));
            this.allFinalSpiele.add(new FinalSpiel("Kleines Finale", LocalTime.of(15, 35)));
            this.allFinalSpiele.add(new FinalSpiel("Finale", LocalTime.of(16, 0)));
        } else if (this.allFinalSpiele.size() == 6) {
            this.allFinalSpiele.add(new FinalSpiel("Kleines Finale", LocalTime.of(15, 35)));
            this.allFinalSpiele.add(new FinalSpiel("Finale", LocalTime.of(16, 0)));
        }
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

    public List<TeamRank> getResult() {
        this.result = new ArrayList<>();
        for (FinalSpiel finalSpiel : this.allFinalSpiele) {
            switch (finalSpiel.getIndicator()) {
                case "Spiel um Platz 11":
                    calculateRank(finalSpiel, 11);
                    break;
                case "Spiel um Platz 9":
                    calculateRank(finalSpiel, 9);
                    break;
                case "Spiel um Platz 7":
                    calculateRank(finalSpiel, 7);
                    break;
                case "Spiel um Platz 5":
                    calculateRank(finalSpiel, 5);
                    break;
                case "Kleines Finale":
                    calculateRank(finalSpiel, 3);
                    break;
                case "Finale":
                    calculateRank(finalSpiel, 1);
                    break;
            }
        }
        this.result.sort(Comparator.comparingInt(tr -> tr.rank));
        return this.result;
    }

    private void calculateRank(FinalSpiel finalSpiel, int rank) {
        if (finalSpiel.getToreAwayTeam() != null && finalSpiel.getToreHomeTeam() != null) {
            if (finalSpiel.getToreHomeTeam() < finalSpiel.getToreAwayTeam()) {
                this.result.add(new TeamRank(finalSpiel.getHomeTeam(), rank + 1));
                this.result.add(new TeamRank(finalSpiel.getAwayTeam(), rank));
            } else {
                this.result.add(new TeamRank(finalSpiel.getAwayTeam(), rank + 1));
                this.result.add(new TeamRank(finalSpiel.getHomeTeam(), rank));
            }
        }
    }
}