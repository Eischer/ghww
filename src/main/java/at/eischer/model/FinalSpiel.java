package at.eischer.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@NamedQueries({
        @NamedQuery(name = "FinalSpiel.getAllFinalSpiele", query = "SELECT fs FROM FinalSpiel fs order by fs.zeit ASC"),
        @NamedQuery(name = "FinalSpiel.getFinalSpielById", query = "SELECT fs FROM FinalSpiel fs where fs.id = :id")
})
public class FinalSpiel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalTime zeit;

    private String indicator;

    @ManyToOne
    @JoinColumn(name = "awayTeamFk")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "homeTeamFk")
    private Team awayTeam;

    private Integer toreHomeTeam;

    private Integer toreAwayTeam;

    public FinalSpiel() {
    }

    public FinalSpiel(String indicator, LocalTime zeit) {
        this.indicator = indicator;
        this.zeit = zeit;
    }

    public FinalSpiel(String indicator, LocalTime zeit, Team homeTeam, Team awayTeam) {
        this(indicator, zeit);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Integer getToreHomeTeam() {
        return toreHomeTeam;
    }

    public void setToreHomeTeam(Integer toreHomeTeam) {
        this.toreHomeTeam = toreHomeTeam;
    }

    public Integer getToreAwayTeam() {
        return toreAwayTeam;
    }

    public void setToreAwayTeam(Integer toreAwayTeam) {
        this.toreAwayTeam = toreAwayTeam;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public LocalTime getZeit() {
        return zeit;
    }

    public void setZeit(LocalTime zeit) {
        this.zeit = zeit;
    }
}
