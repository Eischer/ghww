package at.eischer.view;

import at.eischer.model.Team;
import at.eischer.services.TeamService;
import at.eischer.session.CurrentUser;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class TeamView implements Serializable {

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
        allTeams = teamService.findAllteams();
        beerCountForTeamId = new HashMap<>();

        ranking = new ArrayList<>();

        int currentRank = 1;
        int rankCounter = 1;
        for (Team team : teamService.findAllteamsOrderBySeiderl()) {
            if (ranking.isEmpty()) {
                ranking.add(new SeiderlRanking(currentRank, team));
            } else {
                if (ranking.get(ranking.size() - 1).getTeam().getSeiderlCounter() == team.getSeiderlCounter()) {
                    ranking.add(new SeiderlRanking(currentRank, team));
                } else {
                    currentRank = rankCounter;
                    ranking.add(new SeiderlRanking(currentRank, team));
                }
            }
            rankCounter++;
        }
    }

    public String saveTeam() {
        teamViewBean.saveTeam();
        return "/teams?faces-redirect=true";
    }

    public void incrementSeidl() {
        if (checkIfTimeIsValid()) {
            String tIdString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tid");
            long tId = Long.parseLong(tIdString);
            Team teamToRefreshSeidl = teamService.findTeamById(tId);
            if (teamToRefreshSeidl != null) {
                teamService.incrementSeiderl(teamToRefreshSeidl.getId(), beerCountForTeamId.get(teamToRefreshSeidl.getId()));
                allTeams = teamService.findAllteams();
                beerCountForTeamId.put(teamToRefreshSeidl.getId(), 1);
            }
        }
    }

    public void decrementSeidl() {
        if (checkIfTimeIsValid()) {
            String tIdString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tid");
            long tId = Long.parseLong(tIdString);
            Team teamToRefreshSeidl = teamService.findTeamById(tId);
            if (teamToRefreshSeidl != null) {
                teamService.decrementSeiderl(teamToRefreshSeidl.getId(), beerCountForTeamId.get(teamToRefreshSeidl.getId()));
                allTeams = teamService.findAllteams();
                beerCountForTeamId.put(teamToRefreshSeidl.getId(), 1);
            }
        }
    }


    public void incrementKruegerl() {
        if (checkIfTimeIsValid()) {
            String tIdString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tid");
            long tId = Long.parseLong(tIdString);
            Team teamToRefreshKruegl = teamService.findTeamById(tId);
            if (teamToRefreshKruegl != null) {
                teamService.incrementSeiderl(teamToRefreshKruegl.getId(), beerCountForTeamId.get(teamToRefreshKruegl.getId()) * 1.5F);
                allTeams = teamService.findAllteams();
                beerCountForTeamId.put(teamToRefreshKruegl.getId(), 1);
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
            Team teamToRemove = teamService.findTeamById(tid);
            if (teamToRemove != null) {
                if (teamToRemove.getLogoPath() != null) {
                    Path pathToLogo = Paths.get(System.getProperty("jboss.server.data.dir"), "logos", teamToRemove.getLogoPath());
                    Files.delete(pathToLogo);
                }
                teamService.removeTeam(teamToRemove);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfTimeIsValid() {
        return LocalTime.now().getHour() < 19 && LocalTime.now().getHour() >= 9;
    }

    // GETTER - SETTER Section

    public List<Team> getAllTeams() {
        this.allTeams = teamService.findAllteams();
        return allTeams;
    }

    public void setAllTeams(List<Team> allTeams) {
        this.allTeams = allTeams;
    }

    public String getLogo(long teamId) {
        Team team = teamService.findTeamById(teamId);
        return "/logos/" + team.getLogoPath();
    }

    public TeamViewBean getTeamViewBean() {
        return teamViewBean;
    }

    public void setTeamViewBean(TeamViewBean teamViewBean) {
        this.teamViewBean = teamViewBean;
    }

    public List<SeiderlRanking> getRanking() {
        init();
        return ranking;
    }

    public void setRanking(List<SeiderlRanking> ranking) {
        this.ranking = ranking;
    }

    public Map<Long, Integer> getBeerCount() {
        return beerCountForTeamId;
    }

    public void setBeerCount(Map<Long, Integer> beerCount) {
        this.beerCountForTeamId = beerCount;
    }
}
