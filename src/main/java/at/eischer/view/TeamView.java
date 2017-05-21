package at.eischer.view;

import at.eischer.model.Team;
import at.eischer.services.TeamService;
import at.eischer.session.CurrentUser;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
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

    public String saveTeam () {
        teamViewBean.saveTeam();
        allTeams = teamService.findAllteams();
        return "/teams?faces-redirect=true";
    }

    public void incrementSeidl(long teamId) {
        if (checkIfTimeIsValid()) {
            teamService.incrementSeiderl(teamId, beerCountForTeamId.get(teamId));
            allTeams = teamService.findAllteams();
        }
        beerCountForTeamId.put(teamId, 1);
    }

    public void decrementSeidl(long teamId) {
        if (checkIfTimeIsValid()) {
            teamService.decrementSeiderl(teamId, beerCountForTeamId.get(teamId));
            allTeams = teamService.findAllteams();
        }
        beerCountForTeamId.put(teamId, 1);
    }


    public void incrementKruegerl(long teamId) {
        if (checkIfTimeIsValid()) {
            teamService.incrementSeiderl(teamId, beerCountForTeamId.get(teamId) * 1.5F);
            allTeams = teamService.findAllteams();
        }
        beerCountForTeamId.put(teamId, 1);
    }

    public void decrementKruegerl(long teamId) {
        if (checkIfTimeIsValid()) {
            teamService.decrementSeiderl(teamId, beerCountForTeamId.get(teamId) * 1.5F);
            allTeams = teamService.findAllteams();
        }
        beerCountForTeamId.put(teamId, 1);
    }

    public void remove(Team teamToRemove) {
        try {
            if(teamToRemove.getLogoPath() != null) {
                Path pathToLogo = Paths.get(System.getProperty("jboss.server.data.dir"), "logos", teamToRemove.getLogoPath());
                Files.delete(pathToLogo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        teamService.removeTeam(teamToRemove);
        allTeams = teamService.findAllteams();
    }

    public boolean checkIfTimeIsValid() {
        return LocalTime.now().getHour() < 19 && LocalTime.now().getHour() >= 9;
    }

    // GETTER - SETTER Section

    public List<Team> getAllTeams() {
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
