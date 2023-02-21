package at.eischer.view;

import at.eischer.model.Team;
import at.eischer.services.TeamService;
import at.eischer.session.CurrentUser;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class TeamView {

    private List<Team> allTeams;

    private List<SeiderlRanking> ranking;

    @Inject
    CurrentUser currentUser;

    @Inject
    TeamService teamService;

    @Inject
    private TeamViewBean teamViewBean;

    private Map<Long, Integer> beerCountForTeamId;

    @PostConstruct
    public void init() {
        this.allTeams = teamService.findAllteams();
        this.beerCountForTeamId = new HashMap<>();
        this.ranking = new ArrayList<>();
        this.teamViewBean.setParticipateOnSeidlWertung(true);

        int currentRank = 1;
        int rankCounter = 1;
        for (Team team : this.teamService.findAllteamsOrderBySeiderl()) {
            if (team.isParticipateOnSeidlWertung()) {
                if (this.ranking.isEmpty()) {
                    this.ranking.add(new SeiderlRanking(currentRank, team));
                } else {
                    if (this.ranking.get(ranking.size() - 1).getTeam().getSeiderlCounter() == team.getSeiderlCounter()) {
                        this.ranking.add(new SeiderlRanking(currentRank, team));
                    } else {
                        currentRank = rankCounter;
                        this.ranking.add(new SeiderlRanking(currentRank, team));
                    }
                }
                rankCounter++;
            }
        }
    }

    public String saveTeam() {
        this.teamViewBean.saveTeam();
        return "/teams?faces-redirect=true";
    }

    public void incrementSeidl() {
        if (checkIfTimeIsValid()) {
            String tIdString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tid");
            long tId = Long.parseLong(tIdString);
            Team teamToRefreshSeidl = this.teamService.findTeamById(tId);
            if (teamToRefreshSeidl != null) {
                this.teamService.incrementSeiderl(teamToRefreshSeidl.getId(), this.beerCountForTeamId.get(teamToRefreshSeidl.getId()));
                this.allTeams = this.teamService.findAllteams();
                this.beerCountForTeamId.put(teamToRefreshSeidl.getId(), 1);
            }
        }
    }

    public void decrementSeidl() {
        if (checkIfTimeIsValid()) {
            String tIdString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tid");
            long tId = Long.parseLong(tIdString);
            Team teamToRefreshSeidl = this.teamService.findTeamById(tId);
            if (teamToRefreshSeidl != null) {
                this.teamService.decrementSeiderl(teamToRefreshSeidl.getId(), this.beerCountForTeamId.get(teamToRefreshSeidl.getId()));
                this.allTeams = teamService.findAllteams();
                this.beerCountForTeamId.put(teamToRefreshSeidl.getId(), 1);
            }
        }
    }


    public void incrementKruegerl() {
        if (checkIfTimeIsValid()) {
            String tIdString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tid");
            long tId = Long.parseLong(tIdString);
            Team teamToRefreshKruegl = this.teamService.findTeamById(tId);
            if (teamToRefreshKruegl != null) {
                this.teamService.incrementSeiderl(teamToRefreshKruegl.getId(), this.beerCountForTeamId.get(teamToRefreshKruegl.getId()) * 1.5F);
                this.allTeams = teamService.findAllteams();
                this.beerCountForTeamId.put(teamToRefreshKruegl.getId(), 1);
            }
        }
    }

    public void decrementKruegerl() {
        if (checkIfTimeIsValid()) {
            String tIdString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tid");
            long tId = Long.parseLong(tIdString);
            Team teamToRefreshKruegl = teamService.findTeamById(tId);
            if (teamToRefreshKruegl != null) {
                teamService.decrementSeiderl(teamToRefreshKruegl.getId(), beerCountForTeamId.get(teamToRefreshKruegl.getId()) * 1.5F);
                allTeams = teamService.findAllteams();
                beerCountForTeamId.put(teamToRefreshKruegl.getId(), 1);
            }
        }
    }

    public void remove() {
        String teamToRemovId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tid");
        try {
            long tid = Long.parseLong(teamToRemovId);
            Team teamToRemove = this.teamService.findTeamById(tid);
            if (teamToRemove != null) {
                if (teamToRemove.getLogoPath() != null) {
                    Path pathToLogo = Paths.get(System.getProperty("jboss.server.data.dir"), "logos", teamToRemove.getLogoPath());
                    Files.delete(pathToLogo);
                }
                this.teamService.remove(teamToRemove);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfTimeIsValid() {
        return LocalTime.now().getHour() < 19 && LocalTime.now().getHour() >= 9;
    }

    // GETTER - SETTER Section

    public List<Team> getAllTeamsWhichParticipateOnSeidlWertung() {
        this.allTeams = this.teamService.findAllteamsWhichParticipateOnSeidlWertung();
        return this.allTeams;
    }

    public List<Team> getAllTeams() {
        this.allTeams = this.teamService.findAllteams();
        return this.allTeams;
    }

    public void setAllTeams(List<Team> allTeams) {
        this.allTeams = allTeams;
    }

    public String getLogo(long teamId) {
        Team team = this.teamService.findTeamById(teamId);
        if (team != null) {
            return "/logos/" + team.getLogoPath();
        }
        return "";
    }

    public TeamViewBean getTeamViewBean() {
        return this.teamViewBean;
    }

    public void setTeamViewBean(TeamViewBean teamViewBean) {
        this.teamViewBean = teamViewBean;
    }

    public List<SeiderlRanking> getRanking() {
        return this.ranking;
    }

    public void setRanking(List<SeiderlRanking> ranking) {
        this.ranking = ranking;
    }

    public Map<Long, Integer> getBeerCount() {
        return this.beerCountForTeamId;
    }

    public void setBeerCount(Map<Long, Integer> beerCount) {
        this.beerCountForTeamId = beerCount;
    }
}
