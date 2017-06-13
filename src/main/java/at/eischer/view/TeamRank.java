package at.eischer.view;

import at.eischer.model.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeamRank {
    int rank;
    Team team;
    int plusGoals;
    int minusGoals;
    int points;
    Map<Long, Team> wins;
    Map<Long, Team> draws;
    Map<Long, Team> loss;

    public TeamRank (Team team) {
        this.team = team;
        this.rank = 0;
        this.plusGoals = 0;
        this.minusGoals = 0;
        this.points = 0;
        this.wins = new HashMap<>();
        this.draws = new HashMap<>();
        this.loss = new HashMap<>();
    }

    public int getRank() {
        return rank;
    }

    public Team getTeam() {
        return team;
    }

    public int getPlusGoals() {
        return plusGoals;
    }

    public int getMinusGoals() {
        return minusGoals;
    }

    public int getPoints() {
        return points;
    }
}
