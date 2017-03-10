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
import java.util.List;

@Named
@RequestScoped
public class TeamView {

    private Team team;

    private List<Team> allTeams;

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
        return "maintainTeams";
    }

    @PostConstruct
    public void init () {
        team = new Team();
        allTeams = teamService.findAllteams();
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

    public List<Team> getAllTeams() {
        return allTeams;
    }

    public void setAllTeams(List<Team> allTeams) {
        this.allTeams = allTeams;
    }

    public Part getLogo() {
        return logo;
    }

    public void setLogo(Part logo) {
        this.logo = logo;
    }
}
