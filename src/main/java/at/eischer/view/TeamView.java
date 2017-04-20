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
import java.util.List;

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

    @PostConstruct
    public void init() {
        allTeams = teamService.findAllteams();

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

    public void increment(int teamId) {
        teamService.incrementSeiderl(teamId);
        allTeams = teamService.findAllteams();
    }

    public void decrement(int teamId) {
        teamService.decrementSeiderl(teamId);
        allTeams = teamService.findAllteams();
    }

    public void remove(int teamId) {
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
            Team team = teamService.findTeamById(Integer.parseInt(id));
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
}
