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
import java.util.List;

@Named
@RequestScoped
public class SpielManagementView {

    private String gruppe;

    private List<Team> teamPerGruppe;

    private int hour;

    private int minute;

    private Team homeTeam;

    private Team awayTeam;

    private List<Integer> hours;

    private List<Integer> minutes;

    private List<Spiel> spielePerGruppe;

    @Inject
    private SpielService spielService;

    @Inject
    private TeamService teamService;

    @PostConstruct
    public void init() {
        this.hours = new ArrayList<>();
        for (int i=0; i<24; i++) {
            this.hours.add(i);
        }
        this.minutes = new ArrayList<>();
        for (int i=0; i<60; i++) {
            this.minutes.add(i);
        }
        this.gruppe = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("currentGruppe");
        if (this.gruppe == null) {
            this.gruppe = "A";
        }
        this.teamPerGruppe = teamService.findTeamsForGruppe(this.gruppe);
        this.spielePerGruppe = spielService.getAllSpielePerGruppe(this.gruppe);
    }

    public void setTeamsDependingOnGroup (String gruppe) {
        this.gruppe = gruppe;
        this.teamPerGruppe = teamService.findTeamsForGruppe(this.gruppe);
        this.spielePerGruppe = spielService.getAllSpielePerGruppe(this.gruppe);
    }

    public String saveGame() {
        LocalTime zeit = LocalTime.of(this.hour, this.minute);
        spielService.save(new Spiel(zeit, this.gruppe, this.homeTeam, this.awayTeam));
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

    public List<Spiel> getSpielePerGruppe() {
        return spielePerGruppe;
    }

    public void setSpielePerGruppe(List<Spiel> spielePerGruppe) {
        this.spielePerGruppe = spielePerGruppe;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public List<Integer> getHours() {
        return hours;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public List<Integer> getMinutes() {
        return minutes;
    }
}
