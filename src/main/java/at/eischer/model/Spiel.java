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
    private Team homeTeam;

    @OneToOne
    private Team awayTeam;

    private int toreTeam1;

    private int toreTeam2;

    public Spiel () {
        //empty constructor for JPA
    }

    public Spiel (Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

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

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team team1) {
        this.homeTeam = team1;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team team2) {
        this.awayTeam = team2;
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
