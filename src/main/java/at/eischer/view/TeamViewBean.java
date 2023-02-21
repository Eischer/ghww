package at.eischer.view;

import at.eischer.model.Team;
import at.eischer.services.FileSaver;
import at.eischer.services.TeamService;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.servlet.http.Part;
import java.nio.file.Path;

@Dependent
public class TeamViewBean{

    private String teamName;

    private Part logo;

    private boolean participateOnSeidlWertung;

    @Inject
    private TeamService teamService;

    @Inject
    private FileSaver fileSaver;

    public void saveTeam () {
        Team team = new Team();
        team.setName(this.teamName);
        team.setSeiderlCounter(0);
        team.setParticipateOnSeidlWertung(this.participateOnSeidlWertung);
        Path filePath = fileSaver.saveFileAndReturnPath(this.logo, "/logos");
        if (filePath != null) {
            team.setLogoPath(filePath.toString().substring(filePath.toString().lastIndexOf('/') + 1));
        }
        teamService.save(team);
        this.participateOnSeidlWertung = true;
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

    public boolean isParticipateOnSeidlWertung() {
        return participateOnSeidlWertung;
    }

    public void setParticipateOnSeidlWertung(boolean participateOnSeidlWertung) {
        this.participateOnSeidlWertung = participateOnSeidlWertung;
    }
}
