package at.eischer.model;

import javax.persistence.*;

@Entity
@NamedQuery(name = "Team.finadAll", query = "SELECT t FROM Team t")
public class Team {

    @Id
    @GeneratedValue
    private int id;
    private String name;

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

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
}
