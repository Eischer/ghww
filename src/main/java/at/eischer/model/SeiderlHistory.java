package at.eischer.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NamedQueries({
        @NamedQuery(name = "SeiderlHistory.getHistory", query = "SELECT h FROM SeiderlHistory h WHERE h.team = :team And h.localDateTime > :currentTime"),
        @NamedQuery(name = "SeiderlHistory.deleteWholeHistory", query = "DELETE FROM SeiderlHistory")
})
public class SeiderlHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private float seiderlCounter;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getSeiderlCounter() {
        return seiderlCounter;
    }

    public void setSeiderlCounter(float seiderlCounter) {
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
