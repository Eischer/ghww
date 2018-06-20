package at.eischer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@Entity
@NamedQueries(value = {
        @NamedQuery(name ="GaberlnItem.allGaberlnItems", query = "SELECT g FROM GaberlnItem g ORDER BY g.gaberlnCounter DESC"),
        @NamedQuery(name = "GaberlnItem.calculateJackpot", query = "Select COUNT(g) FROM GaberlnItem g")
})
public class GaberlnItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private int gaberlnCounter;

    @Transient
    private int rank;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGaberlnCounter() {
        return gaberlnCounter;
    }

    public void setGaberlnCounter(int gaberlcounter) {
        this.gaberlnCounter = gaberlcounter;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
