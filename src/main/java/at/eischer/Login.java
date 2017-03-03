package at.eischer;

import at.eischer.model.Team;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@Named
@SessionScoped
public class Login implements Serializable{

    @PersistenceContext(unitName = "myPersistenceUnit")
    private EntityManager entityManager;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String send() {
        Team team = new Team();
        team.setName("Grasshoppers West Wien");
        entityManager.persist(team);
        return ("index");
    }
}