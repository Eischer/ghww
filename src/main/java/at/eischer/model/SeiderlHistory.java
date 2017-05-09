package at.eischer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NamedQuery(name = "SeiderlHistory.getHistory", query = "SELECT h FROM SeiderlHistory h WHERE h.team = :team And h.localDateTime > :currentTime")
public class SeiderlHistory {

    @Id
    @GeneratedValue
    private int id;

    private int seiderlCounter;

    private LocalDateTime localDateTime;

    @ManyToOne
    @JoinColumn(name = "team_fk")
    private Team team;

    public SeiderlHistory() {
        // Only for JPA
    }

    public SeiderlHistory(Team team) {
        this.seiderlCounter = team.getSeiderlCounter();
        this.localDateTime = LocalDateTime.now();
        this.team = team;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeiderlCounter() {
        return seiderlCounter;
    }

    public void setSeiderlCounter(int seiderlCounter) {
        this.seiderlCounter = seiderlCounter;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public LocalDateTime getLocalDate() {
        return localDateTime;
    }

    public void setLocalDate(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
