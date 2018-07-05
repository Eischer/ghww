package at.eischer.view;

import at.eischer.model.GaberlnItem;
import at.eischer.services.FileSaver;
import at.eischer.services.GaberlnItemService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.nio.file.Path;
import java.util.List;

@Named
@RequestScoped
public class GaberlnItemViewBean {

    @Inject
    private GaberlnItemService gaberlnItemService;

    @Inject
    private FileSaver fileSaver;

    private String name;

    private int gaberlnCounter;

    private List<GaberlnItem> allGaberlnItems;

    private Part playerPhoto;

    private GaberlnItem gaberlPlayer;

    @PostConstruct
    public void init() {
        gaberlPlayer = null;
        this.allGaberlnItems = gaberlnItemService.findAll();
    }

    public String saveGaberlScore() {
        GaberlnItem gaberlnItem = new GaberlnItem();
        gaberlnItem.setName(name);
        gaberlnItem.setGaberlnCounter(gaberlnCounter);
        gaberlnItemService.save(gaberlnItem);
        return "/admin/gaberlnManagement?faces-redirect=true";
    }

    public GaberlnItem getGaberlnItemById(Long gaberlnId) {
        this.allGaberlnItems = gaberlnItemService.findAll();
        if (gaberlnId == null) {
            throw new IllegalArgumentException("no id");
        } else {
            for (GaberlnItem gaberlnItem : this.allGaberlnItems) {
                if (gaberlnId.equals(gaberlnItem.getId())) {
                    return gaberlnItem;
                }
            }
            return null;
        }
    }

    public String getPlayerPhotoPath() {
        List<GaberlnItem> tempAllGaberlnItems = getAllGaberlnItems();
        if(tempAllGaberlnItems == null || tempAllGaberlnItems.isEmpty() || tempAllGaberlnItems.get(0).getPhotoPath() == null) {
            return "/resources/anonymous.jpeg";
        } else {
            return "/gaberlnPlayerPhotos/" + tempAllGaberlnItems.get(0).getPhotoPath();
        }
    }

    public void savePlayerPhoto() {
        if (gaberlPlayer!=null) {
            Path filePath = fileSaver.saveFileAndReturnPath(playerPhoto, "/gaberlnPlayerPhotos");
            gaberlPlayer.setPhotoPath(filePath.toString().substring(filePath.toString().lastIndexOf('/') + 1));
            gaberlnItemService.update(gaberlPlayer);
        }
    }

    public String getJackpot() {
        return gaberlnItemService.getJackpot() + " €";
    }

    // GETTER - SETTER - SECTION
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGaberlnCounter() {
        return gaberlnCounter;
    }

    public void setGaberlnCounter(int gaberlnCounter) {
        this.gaberlnCounter = gaberlnCounter;
    }

    public List<GaberlnItem> getAllGaberlnItems() {
        for (int i = 0; i < allGaberlnItems.size(); i++) {
            allGaberlnItems.get(i).setRank(i + 1);
        }
        return allGaberlnItems.subList(0, Math.min(allGaberlnItems.size(), 25));
    }

    public void setAllGaberlnItems(List<GaberlnItem> allGaberlnItems) {
        this.allGaberlnItems = allGaberlnItems;
    }

    public Part getPlayerPhoto() {
        return playerPhoto;
    }

    public void setPlayerPhoto(Part playerPhoto) {
        this.playerPhoto = playerPhoto;
    }

    public GaberlnItem getGaberlPlayer() {
        return gaberlPlayer;
    }

    public void setGaberlPlayer(GaberlnItem gaberlPlayer) {
        this.gaberlPlayer = gaberlPlayer;
    }
}
