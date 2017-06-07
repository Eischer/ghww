package at.eischer.model;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@NamedQuery(name = "Spiel.getSpielePerGruppe", query = "SELECT s FROM Spiel s WHERE s.gruppe = :gruppe")
public class Spiel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long spielId;

    private String gruppe;

    private LocalTime zeit;

    @ManyToOne
    @JoinColumn(name = "awayTeamFk")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "homeTeamFk")
    private Team awayTeam;

    private Integer toreHomeTeam;

    private Integer toreAwayTeam;

    public Spiel () {
        //empty constructor for JPA
    }

    public Spiel (LocalTime zeit, String gruppe, Team homeTeam, Team awayTeam) {
        this.zeit = zeit;
        this.gruppe = gruppe;
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

    public Integer getToreHomeTeam() {
        return toreHomeTeam;
    }

    public void setToreHomeTeam(Integer toreTeam1) {
        this.toreHomeTeam = toreTeam1;
    }

    public Integer getToreAwayTeam() {
        return toreAwayTeam;
    }

    public void setToreAwayTeam(Integer toreTeam2) {
        this.toreAwayTeam = toreTeam2;
    }

    public String getGruppe() {
        return gruppe;
    }

    public void setGruppe(String gruppe) {
        this.gruppe = gruppe;
    }

}
