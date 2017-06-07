package at.eischer.model;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t ORDER BY t.name"),
        @NamedQuery(name = "Team.findForGruppe", query = "SELECT t from Team t WHERE t.gruppe = :gruppe"),
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

    private String gruppe;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team", orphanRemoval = true)
    private List<SeiderlHistory> history;

    private String logoPath;

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "homeTeam", orphanRemoval = true)
    private List<Spiel> homeSpiele;

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "awayTeam", orphanRemoval = true)
    private List<Spiel> awaySpiele;

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

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public List<SeiderlHistory> getHistory() {
        return history;
    }

    public void setHistory(List<SeiderlHistory> history) {
        this.history = history;
    }

    public String getGruppe() {
        return gruppe;
    }

    public void setGruppe(String gruppe) {
        this.gruppe = gruppe;
    }

    public List<Spiel> getHomeSpiele() {
        return homeSpiele;
    }

    public void setHomeSpiele(List<Spiel> homeSpiele) {
        this.homeSpiele = homeSpiele;
    }

    public List<Spiel> getAwaySpiele() {
        return awaySpiele;
    }

    public void setAwaySpiele(List<Spiel> awaySpiele) {
        this.awaySpiele = awaySpiele;
    }

}
