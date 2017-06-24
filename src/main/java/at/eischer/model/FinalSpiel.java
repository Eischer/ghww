package at.eischer.model;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@NamedQuery(name = "FinalSpiel.getAllFinalSpiele", query = "SELECT fs FROM FinalSpiel fs")
public class FinalSpiel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalTime zeit;

    @ManyToOne
    @JoinColumn(name = "awayTeamFk")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "homeTeamFk")
    private Team awayTeam;

    private int toreHomeTeam;

    private int toreAwayTeam;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalTime getZeit() {
        return zeit;
    }

    public void setZeit(LocalTime zeit) {
        this.zeit = zeit;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getToreHomeTeam() {
        return toreHomeTeam;
    }

    public void setToreHomeTeam(int toreHomeTeam) {
        this.toreHomeTeam = toreHomeTeam;
    }

    public int getToreAwayTeam() {
        return toreAwayTeam;
    }

    public void setToreAwayTeam(int toreAwayTeam) {
        this.toreAwayTeam = toreAwayTeam;
    }

}
