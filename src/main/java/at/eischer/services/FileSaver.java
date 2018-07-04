package at.eischer.services;

import org.apache.commons.io.FilenameUtils;

import javax.ejb.Stateless;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Stateless
public class FileSaver {

    public Path saveFileAndReturnPath(Part file, String directory) {
        try {
            if (file != null) {
                Path folder = Paths.get(System.getProperty("jboss.server.data.dir") + directory);
                if (!folder.toFile().exists()) {
                    Files.createDirectories(folder);
                }
                String filename = FilenameUtils.getBaseName(file.getSubmittedFileName());
                String extension = FilenameUtils.getExtension(file.getSubmittedFileName());
                Path filePath = Files.createTempFile(folder, filename + "-", "." + extension);

                try (InputStream input = file.getInputStream()) {
                    Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
                }
                return filePath;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
