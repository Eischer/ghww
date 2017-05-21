package at.eischer.view;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class SpielManagementView {

    private String gruppe;

    public String getGruppe() {
        return gruppe;
    }

    public void setGruppe(String gruppe) {
        this.gruppe = gruppe;
    }
}
