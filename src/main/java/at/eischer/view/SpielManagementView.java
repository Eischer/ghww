package at.eischer.view;

import at.eischer.model.Spiel;
import at.eischer.model.Team;
import at.eischer.services.SpielService;
import at.eischer.services.TeamService;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class SpielManagementView implements Serializable {

    private String gruppe;

    private List<Team> teamPerGruppe;

    private List<Spiel> spielePerGruppe;

    private SpielInput spielInput;

    @Inject
    private SpielService spielService;

    @Inject
    private TeamService teamService;

    private TeamRank[] result;

    @PostConstruct
    public void init() {
        spielInput = new SpielInput();
        this.gruppe = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("currentGruppe");
        if (this.gruppe == null) {
            this.gruppe = "A";
        }
        this.teamPerGruppe = teamService.findTeamsForGruppe(this.gruppe);
        this.spielePerGruppe = spielService.getAllSpielePerGruppe(this.gruppe);
        this.result = new TeamRank[teamPerGruppe.size()];
        this.result = calculateStandings();
    }

    private TeamRank[] calculateStandings() {
        List<Team> allTeamsForGroup = teamService.findTeamsForGruppe(this.gruppe);
        Map<Long, TeamRank> standingsAsMap = new HashMap<>();
        for (Team team : allTeamsForGroup) {
            standingsAsMap.put(team.getId(), new TeamRank(team));
        }

        List<Spiel> allSpieleForGroup = spielService.getAllSpielePerGruppe(this.gruppe);
        standingsAsMap = collectDataFromSpiele(standingsAsMap, allSpieleForGroup);
        return calculateStandings(standingsAsMap);
    }

    private TeamRank[] calculateStandings(Map<Long, TeamRank> standingsAsMap) {
        List<TeamRank> sortedByPoints = new ArrayList<>(standingsAsMap.values());
        sortedByPoints.sort(Comparator.comparingInt(TeamRank::getPoints).reversed());

        List<List<TeamRank>> equalTeams = getEqualTeamsAsListsAndSetDistingTeamsToResult(sortedByPoints);

        for (List<TeamRank> listOfEqualTeams : equalTeams) {
            int currentRank = listOfEqualTeams.get(0).rank;
            Map<Long, TeamRank> subgroup = new HashMap<>();
            for (TeamRank oneTeam : listOfEqualTeams) {
                subgroup.put(oneTeam.getTeam().getId(), new TeamRank(oneTeam.getTeam()));
            }

            List<Team> subGroupTeams = listOfEqualTeams.stream().map(TeamRank::getTeam).collect(Collectors.toList());
            List<Spiel> allSpielOfTeams = spielService.getAllSpieleWithTeams(subGroupTeams);
            subgroup = collectDataFromSpiele(subgroup, allSpielOfTeams);

            listOfEqualTeams = new ArrayList<>(subgroup.values());

            Set<TeamRank> stillEqualTeams = sortTeamSubListByPointsAndGoals(listOfEqualTeams);
            listOfEqualTeams.removeAll(stillEqualTeams);

            int startIndex = currentRank - 1;
            for (int i=startIndex; i<(startIndex + listOfEqualTeams.size()); i++) {
                long teamId = listOfEqualTeams.get(i-startIndex).getTeam().getId();
                standingsAsMap.get(teamId).rank = currentRank++;
                this.result[i] = standingsAsMap.get(teamId);
            }

            lastTryForRankingWithTotalGoals(sortedByPoints);
        }

        return this.result;
    }

    private void lastTryForRankingWithTotalGoals(List<TeamRank> sortedByPoints) {
        Set<TeamRank> atLastStillEqualTeams = sortTeamSubListByPointsAndGoals(sortedByPoints);
        int i = 0;
        for (TeamRank teamRank : sortedByPoints) {
            if (!alreadyInResult(teamRank.getTeam().getId())) {
                if(!inLastStillEqualTeams(teamRank.getTeam().getId(), atLastStillEqualTeams)) {
                    this.result[i] = teamRank;
                } else {
                    teamRank.equal = true;
                    this.result[i] = teamRank;
                }
            }
            i++;
        }
    }

    private boolean inLastStillEqualTeams(long id, Set<TeamRank> atLastStillEqualTeams) {
        for (TeamRank lastStillEqualTeam : atLastStillEqualTeams) {
            if (lastStillEqualTeam.getTeam().getId() == id) {
                return true;
            }
        }
        return false;
    }

    private boolean alreadyInResult(long id) {
        for (TeamRank resultTeamRank : result) {
            if (resultTeamRank != null && resultTeamRank.getTeam().getId() == id) {
                return true;
            }
        }
        return false;
    }


    private Set<TeamRank> sortTeamSubListByPointsAndGoals(List<TeamRank> listOfEqualTeams) {
        Set<TeamRank> stillEqualTeams = new HashSet<>();
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
                        stillEqualTeams.add(o1);
                        stillEqualTeams.add(o2);
                        return 0;
                    }
                }
            }
        });
        return stillEqualTeams;
    }

    private List<List<TeamRank>> getEqualTeamsAsListsAndSetDistingTeamsToResult(List<TeamRank> sortedByPoints) {
        int counter = 0;
        int teamRank = 1;
        int lastPoints = -1;
        List<List<TeamRank>> equalTeams = new ArrayList<>();
        for (int i=0; i<sortedByPoints.size(); i++) {
            if (sortedByPoints.get(i).getPoints() == lastPoints) {
                sortedByPoints.get(i).rank = teamRank;
                equalTeams.get(equalTeams.size() - 1).add(sortedByPoints.get(i));
            } else {
                List<TeamRank> newEqualityList = new ArrayList<>();
                teamRank = counter + 1;
                sortedByPoints.get(i).rank = teamRank;
                newEqualityList.add(sortedByPoints.get(i));
                equalTeams.add(newEqualityList);
                lastPoints = sortedByPoints.get(i).getPoints();
                if (i == sortedByPoints.size()-1 || sortedByPoints.get(i).getPoints() != sortedByPoints.get(i+1).getPoints()) {
                    result[counter] = sortedByPoints.get(i);
                }
            }
            counter++;
        }
        return equalTeams;
    }

    /**
     * Normally these method will collect the Data for a group, but if multiple Teams have the same Points t
     */
    private Map<Long, TeamRank> collectDataFromSpiele(Map<Long, TeamRank> standingsAsMap, List<Spiel> allSpieleForCalculation) {
        for (Spiel spiel : allSpieleForCalculation) {
            if (spiel.getToreHomeTeam() != null && spiel.getToreAwayTeam() != null) {
                TeamRank homeTeam = standingsAsMap.get(spiel.getHomeTeam().getId());
                TeamRank awayTeam = standingsAsMap.get(spiel.getAwayTeam().getId());
                if (spiel.getToreHomeTeam() > spiel.getToreAwayTeam()) {
                    homeTeam.points += 3;
                    setTore(spiel, homeTeam, awayTeam);
                    standingsAsMap.put(homeTeam.getTeam().getId(), homeTeam);
                    standingsAsMap.put(awayTeam.getTeam().getId(), awayTeam);
                } else if (spiel.getToreHomeTeam().intValue() == spiel.getToreAwayTeam().intValue()) {
                    homeTeam.points++;
                    awayTeam.points++;
                    setTore(spiel, homeTeam, awayTeam);
                    standingsAsMap.put(homeTeam.getTeam().getId(), homeTeam);
                    standingsAsMap.put(awayTeam.getTeam().getId(), awayTeam);
                } else {
                    awayTeam.points += 3;
                    setTore(spiel, homeTeam, awayTeam);
                    standingsAsMap.put(homeTeam.getTeam().getId(), homeTeam);
                    standingsAsMap.put(awayTeam.getTeam().getId(), awayTeam);
                }
                return standingsAsMap;
            }
        }
        return standingsAsMap;
    }

    private void setTore(Spiel spiel, TeamRank homeTeam, TeamRank awayTeam) {
        homeTeam.plusGoals += spiel.getToreHomeTeam();
        homeTeam.minusGoals += spiel.getToreAwayTeam();
        awayTeam.plusGoals += spiel.getToreAwayTeam();
        awayTeam.minusGoals += spiel.getToreHomeTeam();
    }

    public void saveGame() {
        LocalTime zeit = LocalTime.of(spielInput.getHour(), spielInput.getMinute());
        spielService.save(new Spiel(zeit, this.gruppe, spielInput.getHomeTeam(), spielInput.getAwayTeam()));
        this.spielePerGruppe = spielService.getAllSpielePerGruppe(this.gruppe);
    }

    public void deleteSpiel(Spiel spiel) {
        spielService.removeSpiel(spiel);
        this.spielePerGruppe = spielService.getAllSpielePerGruppe(this.gruppe);
        this.result = new TeamRank[teamPerGruppe.size()];
        this.result = calculateStandings();
    }

    public void saveResult(Spiel spiel) {
        spielService.update(spiel);
        this.result = new TeamRank[teamPerGruppe.size()];
        this.result = calculateStandings();
    }

    public void deleteResult(Spiel spiel) {
        spiel.setToreHomeTeam(null);
        spiel.setToreAwayTeam(null);
        spielService.update(spiel);
        this.result = new TeamRank[teamPerGruppe.size()];
        this.result = calculateStandings();
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

    public TeamRank[] getResult() {
        return result;
    }

    public void setResult(TeamRank[] result) {
        this.result = result;
    }
}
