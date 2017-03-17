package at.eischer.view;

import at.eischer.model.Team;
import at.eischer.services.TeamService;
import org.apache.commons.io.IOUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

@Named
@RequestScoped
public class CreateTeamView {

    private Team team;

    private Part logo;

    @Inject
    TeamService teamService;

    public String saveTeam () {
        try {
            byte [] logoAsByteArray;
            if (logo != null) {
                InputStream logoAsStream = logo.getInputStream();
                logoAsByteArray = IOUtils.toByteArray(logoAsStream);
                team.setLogo(logoAsByteArray);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        teamService.save(team);
        return "displayAllTeams";
    }

    @PostConstruct
    public void init () {
        team = new Team();
    }


    /*******************************/
    /*** GETTER - SETTER Section ***/
    /*******************************/

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Part getLogo() {
        return logo;
    }

    public void setLogo(Part logo) {
        this.logo = logo;
    }
}
