package at.eischer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SeiderlHistory {

    @Id
    @GeneratedValue
    private int id;
    private int seiderlCounter;
    @ManyToOne
    @JoinColumn(name = "team_fk")
    private Team team;

    public SeiderlHistory() {
        // Only for JPA
    }

    public SeiderlHistory(Team team) {
        this.seiderlCounter = team.getSeiderlCounter();
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
}
