package at.eischer.view;

import at.eischer.model.Team;

public class TeamRank {
    public int rank;

    public Team team;

    public int plusGoals;

    public int minusGoals;

    public int points;

    public boolean equal;

    public TeamRank (Team team) {
        this.team = team;
        this.rank = 0;
        this.plusGoals = 0;
        this.minusGoals = 0;
        this.points = 0;
        this.equal = false;
    }

    public TeamRank (Team team, int rank) {
        this.team = team;
        this.rank = rank;
        this.plusGoals = 0;

        this.minusGoals = 0;
        this.points = 0;
        this.equal = false;
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
