package at.eischer.model;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t ORDER BY t.name"),
        @NamedQuery(name = "Team.findAllOrderBySeiderl", query = "SELECT t FROM Team t ORDER BY t.seiderlCounter DESC"),
        @NamedQuery(name = "Team.getMaxSeiderlCount", query = "SELECT MAX (t.seiderlCounter) FROM Team t"),
        @NamedQuery(name = "Team.incrementSeidlCount", query = "UPDATE Team t SET t.seiderlCounter=t.seiderlCounter + :seidlCount WHERE t.id = :teamId"),
        @NamedQuery(name = "Team.decrementSeidlCount", query = "UPDATE Team t SET t.seiderlCounter=t.seiderlCounter - :seidlCount WHERE t.id = :teamId")
})
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private float seiderlCounter;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team", orphanRemoval = true)
    private List<SeiderlHistory> history;

    private String logoPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setSeiderlCounter(float seiderCounter) {
        this.seiderlCounter = seiderCounter;
    }

    public String getLogo() {
        return logoPath;
    }

    public void setLogo(String logoPath) {
        this.logoPath = logoPath;
    }

    public List<SeiderlHistory> getHistory() {
        return history;
    }

    public void setHistory(List<SeiderlHistory> history) {
        this.history = history;
    }
}
