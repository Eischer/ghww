package at.eischer.view;

import at.eischer.model.Team;

public class TeamRank {
    int rank;
    Team team;
    int plusGoals;
    int minusGoals;
    int points;

    public TeamRank (Team team) {
        this.team = team;
        this.rank = 0;
        this.plusGoals = 0;
        this.minusGoals = 0;
        this.points = 0;
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
