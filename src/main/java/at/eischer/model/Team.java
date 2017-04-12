package at.eischer.model;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t ORDER BY t.name"),
        @NamedQuery(name = "Team.getMaxSeiderlCount", query = "SELECT MAX (t.seiderlCounter) FROM Team t")
})
public class Team {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private int seiderlCounter;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team", orphanRemoval = true)
    private List<SeiderlHistory> history;

    @Lob
    private byte[] logo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setSeiderlCounter(int seiderCounter) {
        this.seiderlCounter = seiderCounter;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public List<SeiderlHistory> getHistory() {
        return history;
    }

    public void setHistory(List<SeiderlHistory> history) {
        this.history = history;
    }
}
