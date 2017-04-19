package at.eischer.view;

import at.eischer.model.Team;

public class SeiderRanking {
    private int rank;

    private Team team;

    public SeiderRanking(int rank, Team team) {
        this.rank = rank;
        this.team = team;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
