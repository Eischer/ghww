package at.eischer.session;

import at.eischer.model.User;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@SessionScoped
public class CurrentUser implements Serializable{

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
