package at.eischer.view;

import at.eischer.model.Team;

public class SeiderlRanking {
    private int rank;

    private Team team;

    public SeiderlRanking(int rank, Team team) {
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
