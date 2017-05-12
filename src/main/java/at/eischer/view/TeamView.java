package at.eischer.view;

import at.eischer.model.Team;
import at.eischer.services.TeamService;
import at.eischer.session.CurrentUser;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
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
        allTeams = teamService.findAllteams();
        beerCountForTeamId = new HashMap<>();

        ranking = new ArrayList<>();

        int currentRank = 1;
        int rankCounter = 1;
        for (Team team : teamService.findAllteamsOrderBySeiderl()) {
            if (ranking.isEmpty()) {
                ranking.add(new SeiderlRanking(currentRank, team));
            } else {
                if (ranking.get(ranking.size()-1).getTeam().getSeiderlCounter() == team.getSeiderlCounter()) {
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
        return "/public/displayAllTeams?faces-redirect=true";
    }

    public void incrementSeidl(long teamId) {
        teamService.incrementSeiderl(teamId, beerCountForTeamId.get(teamId));
        allTeams = teamService.findAllteams();
        beerCountForTeamId.put(teamId, 1);
    }

    public void decrementSeidl(long teamId) {
        teamService.decrementSeiderl(teamId, beerCountForTeamId.get(teamId));
        allTeams = teamService.findAllteams();
        beerCountForTeamId.put(teamId, 1);
    }


    public void incrementKruegerl(long teamId) {
        teamService.incrementSeiderl(teamId, beerCountForTeamId.get(teamId) * 1.5F);
        allTeams = teamService.findAllteams();
        beerCountForTeamId.put(teamId, 1);
    }

    public void decrementKruegerl(long teamId) {
        teamService.decrementSeiderl(teamId, beerCountForTeamId.get(teamId) * 1.5F);
        allTeams = teamService.findAllteams();
        beerCountForTeamId.put(teamId, 1);
    }

    public void remove(long teamId) {
        teamService.removeTeam(teamId);
        allTeams = teamService.findAllteams();
    }

    // GETTER - SETTER Section

    public List<Team> getAllTeams() {
        return allTeams;
    }

    public void setAllTeams(List<Team> allTeams) {
        this.allTeams = allTeams;
    }

    public StreamedContent getLogo() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        }

        else {
            String id = context.getExternalContext().getRequestParameterMap()
                    .get("teamId");
            Team team = teamService.findTeamById(Long.parseLong(id));
            byte[] image = team.getLogo();
            return image != null ? new DefaultStreamedContent(new ByteArrayInputStream(image)) : null;
        }
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
