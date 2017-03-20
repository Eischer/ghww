package at.eischer.model;

import javax.persistence.*;

@Entity
@NamedQueries(
        {
                @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t"),
                @NamedQuery(name = "Team.findById", query = "SELECT t FROM Team t WHERE t.id = :teamId ")
        }
)
public class Team {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private int seiderlCounter;

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
}
