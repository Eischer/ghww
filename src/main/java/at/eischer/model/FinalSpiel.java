package at.eischer.model;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@NamedQueries({
        @NamedQuery(name = "FinalSpiel.getAllFinalSpiele", query = "SELECT fs FROM FinalSpiel fs order by fs.ordering"),
        @NamedQuery(name = "FinalSpiel.getFinalSpielById", query = "SELECT fs FROM FinalSpiel fs where fs.id = :id")
})
public class FinalSpiel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int hour;

    private int minute;

    private Integer ordering;

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

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer order) {
        this.ordering = order;
    }

    public int getHour() {
        return hour;
    }

    public void setHour (int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
