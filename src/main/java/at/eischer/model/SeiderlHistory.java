package at.eischer.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SeiderlHistory {

    @Id
    private String id;
    private int recordCounter;
    private int seiderlCounter;
    @ManyToOne
    @JoinColumn(name = "team_fk")
    private Team team;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRecordCounter() {
        return recordCounter;
    }

    public void setRecordCounter(int recordCounter) {
        this.recordCounter = recordCounter;
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
