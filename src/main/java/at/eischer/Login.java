package at.eischer;

import at.eischer.dao.Repository;
import at.eischer.model.Team;
import com.google.common.io.ByteStreams;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

@Named
@SessionScoped
public class Login implements Serializable{

    @Inject
    Repository repository;

    private String name;

    private Part logo;

    @Transactional
    public String send() throws IOException {
        Team team = new Team();
        team.setName("Grasshoppers West Wien");
        InputStream is = logo.getInputStream();
        byte [] logoAsByteArray = ByteStreams.toByteArray(is);
        team.setLogo(logoAsByteArray);
        repository.save(team);
        return ("index");
    }

    /**
     * GETTER AND SETTER
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Part getLogo() {
        return this.logo;
    }

    public void setLogo (Part logo) {
        this.logo = logo;
    }
}