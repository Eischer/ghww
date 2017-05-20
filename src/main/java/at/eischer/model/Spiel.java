package at.eischer.model;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class Spiel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long spielId;

    private LocalTime zeit;

    @OneToOne
    private Team team1;

    @OneToOne
    private Team team2;

    private int toreTeam1;

    private int toreTeam2;

    public LocalTime getZeit() {
        return zeit;
    }

    public void setZeit(LocalTime zeit) {
        this.zeit = zeit;
    }

    public long getSpielId() {
        return spielId;
    }

    public void setSpielId(long spielId) {
        this.spielId = spielId;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public int getToreTeam1() {
        return toreTeam1;
    }

    public void setToreTeam1(int toreTeam1) {
        this.toreTeam1 = toreTeam1;
    }

    public int getToreTeam2() {
        return toreTeam2;
    }

    public void setToreTeam2(int toreTeam2) {
        this.toreTeam2 = toreTeam2;
    }

}
