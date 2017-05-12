package at.eischer.view;

import at.eischer.model.Team;
import at.eischer.services.TeamService;
import org.apache.commons.io.IOUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

@Dependent
public class TeamViewBean {
    private String teamName;

    private Part logo;

    @Inject
    private TeamService teamService;

    public void saveTeam () {
        Team team = new Team();
        team.setName(this.teamName);
        team.setSeiderlCounter(0);
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
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public Part getLogo() {
        return logo;
    }

    public void setLogo(Part logo) {
        this.logo = logo;
    }
}
