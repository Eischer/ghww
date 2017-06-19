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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class SpielManagementView {

    private String gruppe;

    private List<Team> teamPerGruppe;

    private List<Spiel> spielePerGruppe;

    private SpielInput spielInput;

    private List<TeamRank> standings;

    @Inject
    private SpielService spielService;

    @Inject
    private TeamService teamService;

    @PostConstruct
    public void init() {
        spielInput = new SpielInput();
        this.gruppe = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("currentGruppe");
        if (this.gruppe == null) {
            this.gruppe = "A";
        }
        this.teamPerGruppe = teamService.findTeamsForGruppe(this.gruppe);
        this.spielePerGruppe = spielService.getAllSpielePerGruppe(this.gruppe);
        this.standings = calculateStandings();
    }

    private List<TeamRank> calculateStandings() {
        List<Team> allTeamsForGroup = teamService.findTeamsForGruppe(this.gruppe);
        Map<Long, TeamRank> standingsAsMap = new HashMap<>();
        for (Team team : allTeamsForGroup) {
            standingsAsMap.put(team.getId(), new TeamRank(team));
        }

        List<Spiel> allSpieleForGroup = spielService.getAllSpielePerGruppe(this.gruppe);
        collectDataFromSpiele(standingsAsMap, allSpieleForGroup);
        return calculateStandings(standingsAsMap);
    }

    private List<TeamRank> calculateStandings(Map<Long, TeamRank> standingsAsMap) {
        List<TeamRank> sortedByPoints = new ArrayList<>(standingsAsMap.values());
        sortedByPoints.sort(Comparator.comparingInt(TeamRank::getPoints).reversed());

        sortedByPoints.sort((o1, o2) -> {
            if (o1.getPoints() < o2.getPoints()) {
                return 1;
            } else if (o1.getPoints() > o2.getPoints()) {
                return -1;
            } else {
                return 0;
            }
        });


        List<List<TeamRank>> equalTeams = new ArrayList<>();
        int rankCounter = 1;
        int counter = 1;
        int lastPoints = -1;
        for (TeamRank oneTeam : sortedByPoints) {
            if (oneTeam.getPoints() == lastPoints) {
                oneTeam.rank = rankCounter;
                equalTeams.get(equalTeams.size() - 1).add(oneTeam);
            } else {
                List<TeamRank> newEqualityList = new ArrayList<>();
                oneTeam.rank = counter;
                newEqualityList.add(oneTeam);
                equalTeams.add(newEqualityList);
                lastPoints = oneTeam.getPoints();
            }
            counter++;
        }


        for (List<TeamRank> listOfEqualTeams : equalTeams) {
            listOfEqualTeams.sort((o1, o2) -> {
                if (o1.getPoints() < o2.getPoints()) {
                    return 1;
                } else if (o1.getPoints() > o2.getPoints()) {
                    return -1;
                } else {
                    if (o1.getPlusGoals() - o1.getMinusGoals() < o2.getPlusGoals() - o2.getMinusGoals()) {
                        return 1;
                    } else if (o1.getPlusGoals() - o1.getMinusGoals() > o2.getPlusGoals() - o2.getMinusGoals()) {
                        return -1;
                    } else {
                        if (o1.getPlusGoals() < o2.getPlusGoals()) {
                            return 1;
                        } else if (o1.getPlusGoals() > o2.getPlusGoals()) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                }
            });
        }

        return sortedByPoints;
    }

    /**
     * Normally these method will collect the Data for a group, gut if multiple Teams have the same Points t
     */
    private void collectDataFromSpiele(Map<Long, TeamRank> standingsAsMap, List<Spiel> allSpieleForCalculation) {
        for (Spiel spiel : allSpieleForCalculation) {
            if (spiel.getToreHomeTeam() != null && spiel.getToreAwayTeam() != null) {
                TeamRank teamRankForHomeTeam = standingsAsMap.get(spiel.getHomeTeam().getId());
                TeamRank teamRankForAwayTeam = standingsAsMap.get(spiel.getAwayTeam().getId());

                teamRankForHomeTeam = addResultToTeamRank(teamRankForHomeTeam, spiel.getToreHomeTeam(), spiel.getToreAwayTeam(), teamRankForAwayTeam.team);
                standingsAsMap.put(spiel.getHomeTeam().getId(), teamRankForHomeTeam);

                teamRankForAwayTeam = addResultToTeamRank(teamRankForAwayTeam, spiel.getToreAwayTeam(), spiel.getToreHomeTeam(), teamRankForHomeTeam.team);
                standingsAsMap.put(spiel.getAwayTeam().getId(), teamRankForAwayTeam);
            }
        }
    }

    private TeamRank addResultToTeamRank(TeamRank teamRank, int ownTore, int foreignTore, Team opponent) {
        teamRank.plusGoals += ownTore;
        teamRank.minusGoals += foreignTore;
        if (ownTore > foreignTore) {
            teamRank.points += 3;
            teamRank.wins.put(opponent.getId(), opponent);
        } else if (ownTore == foreignTore) {
            teamRank.points++;
            teamRank.draws.put(opponent.getId(), opponent);
        } else {
            teamRank.loss.put(opponent.getId(), opponent);
        }
        return teamRank;
    }

    public void saveGame() {
        LocalTime zeit = LocalTime.of(spielInput.getHour(), spielInput.getMinute());
        spielService.save(new Spiel(zeit, this.gruppe, spielInput.getHomeTeam(), spielInput.getAwayTeam()));
        this.spielePerGruppe = spielService.getAllSpielePerGruppe(this.gruppe);
    }

    public void deleteSpiel(Spiel spiel) {
        spielService.removeSpiel(spiel);
        this.spielePerGruppe = spielService.getAllSpielePerGruppe(this.gruppe);
        this.standings = calculateStandings();
    }

    public void saveResult(Spiel spiel) {
        spielService.update(spiel);
        this.standings = calculateStandings();
    }

    public void deleteResult(Spiel spiel) {
        spiel.setToreHomeTeam(null);
        spiel.setToreAwayTeam(null);
        spielService.update(spiel);
        this.standings = calculateStandings();
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

    public List<TeamRank> getStandings() {
        return standings;
    }

    public void setStandings(List<TeamRank> standings) {
        this.standings = standings;
    }
}
