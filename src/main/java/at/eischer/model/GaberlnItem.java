package at.eischer.model;

import jakarta.persistence.*;

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

    private String photoPath;

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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
