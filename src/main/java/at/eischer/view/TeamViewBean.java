package at.eischer.view;

import at.eischer.model.Team;
import at.eischer.services.TeamService;
import org.apache.commons.io.FilenameUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Dependent
public class TeamViewBean implements Serializable{

    private String teamName;

    private Part logo;

    @Inject
    private TeamService teamService;

    public void saveTeam () {
        Team team = new Team();
        team.setName(this.teamName);
        team.setSeiderlCounter(0);
        try {
            if (logo != null) {
                Path folder = Paths.get(System.getProperty("jboss.server.data.dir") + "/logos");
                if (!Files.exists(folder)) {
                    Files.createDirectories(folder);
                }
                String filename = FilenameUtils.getBaseName(logo.getSubmittedFileName());
                String extension = FilenameUtils.getExtension(logo.getSubmittedFileName());
                Path filePath = Files.createTempFile(folder, filename + "-", "." + extension);

                try (InputStream input = logo.getInputStream()) {
                    Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
                    team.setLogoPath(filePath.toString().substring(filePath.toString().lastIndexOf("/") + 1));
                }
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
