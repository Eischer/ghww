package at.eischer.view;

import at.eischer.model.GaberlnItem;
import at.eischer.services.GaberlnItemService;
import at.eischer.session.CurrentUser;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class GaberlnItemViewBean {

    private String name;

    private int gaberlnCounter;

    private List<GaberlnItem> allGaberlnItems;

    @Inject
    CurrentUser currentUser;

    @Inject
    private GaberlnItemService gaberlnItemService;

    @PostConstruct
    public void init() {
        this.allGaberlnItems = gaberlnItemService.findAll();
    }

    public String saveGaberlScore() {
        GaberlnItem gaberlnItem = new GaberlnItem();
        gaberlnItem.setName(name);
        gaberlnItem.setGaberlnCounter(gaberlnCounter);
        gaberlnItemService.save(gaberlnItem);
        return "/admin/gaberlnManagement?faces-redirect=true";
    }

    public String removeGaberlnItem(GaberlnItem gaberlnItem) {
        gaberlnItemService.remove(gaberlnItem);
        return "/admin/gaberlnManagement?faces-redirect=true";
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
        return allGaberlnItems.subList(0,Math.min(allGaberlnItems.size(), 10));
    }

    public void setAllGaberlnItems(List<GaberlnItem> allGaberlnItems) {
        this.allGaberlnItems = allGaberlnItems;
    }
}
