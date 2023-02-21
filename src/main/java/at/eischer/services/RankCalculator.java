package at.eischer.services;

import at.eischer.model.Spiel;
import at.eischer.model.Team;
import at.eischer.view.TeamRank;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
public class RankCalculator {

    @Inject
    private TeamService teamService;

    @Inject
    private SpielService spielService;

    public TeamRank[] calculateStandings(String gruppe, TeamRank[] results) {
        List<Team> allTeamsForGroup = teamService.findTeamsForGruppe(gruppe);
        Map<Long, TeamRank> standingsAsMap = new HashMap<>();
        for (Team team : allTeamsForGroup) {
            standingsAsMap.put(team.getId(), new TeamRank(team));
        }

        List<Spiel> allSpieleForGroup = spielService.getAllSpielePerGruppe(gruppe);
        standingsAsMap = collectDataFromSpiele(standingsAsMap, allSpieleForGroup);
        return calculateStandings(standingsAsMap, results);
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
            }
        }
        return standingsAsMap;
    }

    private TeamRank[] calculateStandings(Map<Long, TeamRank> standingsAsMap, TeamRank[] results) {
        List<TeamRank> sortedByPoints = new ArrayList<>(standingsAsMap.values());
        sortedByPoints.sort(Comparator.comparingInt(TeamRank::getPoints).reversed());

        List<List<TeamRank>> equalTeams = getEqualTeamsAsListsAndSetDistingTeamsToResult(sortedByPoints, results);

        for (List<TeamRank> listOfEqualTeams : equalTeams) {
            if (listOfEqualTeams.size() > 1) {
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

                int startIndex = currentRank - 1;
                for (int i = startIndex; i < (startIndex + listOfEqualTeams.size()); i++) {
                    // Wenn Team nun bereits eine eindeutige Platzierung hat (also nicht im Set stillEqualsTeams ist
                    if (!stillEqualTeams.contains(listOfEqualTeams.get(i - startIndex))) {
                        long teamId = listOfEqualTeams.get(i - startIndex).getTeam().getId();
                        standingsAsMap.get(teamId).rank = i + 1;
                        results[i] = standingsAsMap.get(teamId);
                    }
                }
                if (!stillEqualTeams.isEmpty()) {
                    results = lastTryForRankingWithTotalGoals(sortedByPoints, stillEqualTeams, results);
                }
            }
        }

        return results;
    }

    private TeamRank[] lastTryForRankingWithTotalGoals(List<TeamRank> sortedByPoints, Set<TeamRank> teamsWhichStillNeedRanking, TeamRank[] results) {
        Set<TeamRank> atLastStillEqualTeams = sortTeamSubListByPointsAndGoals(sortedByPoints);

        int rankCounter = getFirstFreeTableRank(results);
        int i = rankCounter;

        TeamRank previousTeamRank = null;
        for (TeamRank teamRank : sortedByPoints) {
            if (teamNeedsRanking(teamRank, teamsWhichStillNeedRanking)) {
                if (!inLastStillEqualTeams(teamRank.getTeam().getId(), atLastStillEqualTeams)) {
                    teamRank.rank = ++rankCounter;
                    results[i] = teamRank;
                } else {
                    teamRank.equal = true;
                    ArrayList<TeamRank> twoTeams = new ArrayList<>();
                    twoTeams.add(previousTeamRank);
                    twoTeams.add(teamRank);
                    // Wenn es schon ein vorheriges Team gab check ob das current Team auch equal zu previous ist
                    if (previousTeamRank != null && !sortTeamSubListByPointsAndGoals(twoTeams).isEmpty()) {
                        teamRank.rank = rankCounter;
                    } else {
                        rankCounter = i + 1;
                        teamRank.rank = rankCounter;
                        previousTeamRank = teamRank;
                    }
                    results[i] = teamRank;
                }
                i++;
            }
        }
        return results;
    }

    private boolean teamNeedsRanking(TeamRank teamRank, Set<TeamRank> teamsWhichStillNeedRanking) {
        for (TeamRank currentTeamRank : teamsWhichStillNeedRanking) {
            if (currentTeamRank.getTeam().getId() == teamRank.getTeam().getId()) {
                return true;
            }
        }
        return false;
    }

    private int getFirstFreeTableRank(TeamRank[] results) {
        for (int i = 0; i<results.length; i++) {
            if (results[i]==null) {
                return i;
            }
        }
        throw new RuntimeException("Das hätte nicht passieren dürfen");
    }

    private boolean inLastStillEqualTeams(long id, Set<TeamRank> atLastStillEqualTeams) {
        for (TeamRank lastStillEqualTeam : atLastStillEqualTeams) {
            if (lastStillEqualTeam.getTeam().getId() == id) {
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

    private List<List<TeamRank>> getEqualTeamsAsListsAndSetDistingTeamsToResult(List<TeamRank> sortedByPoints, TeamRank[] results) {
        int counter = 0;
        int teamRank = 1;
        int lastPoints = -1;
        List<List<TeamRank>> equalTeams = new ArrayList<>();
        for (int i = 0; i < sortedByPoints.size(); i++) {
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
                if (i == sortedByPoints.size() - 1 || sortedByPoints.get(i).getPoints() != sortedByPoints.get(i + 1).getPoints()) {
                    results[counter] = sortedByPoints.get(i);
                }
            }
            counter++;
        }
        return equalTeams;
    }

    private void setTore(Spiel spiel, TeamRank homeTeam, TeamRank awayTeam) {
        homeTeam.plusGoals += spiel.getToreHomeTeam();
        homeTeam.minusGoals += spiel.getToreAwayTeam();
        awayTeam.plusGoals += spiel.getToreAwayTeam();
        awayTeam.minusGoals += spiel.getToreHomeTeam();
    }
}
