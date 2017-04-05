package at.eischer.view;

import at.eischer.services.UserService;
import at.eischer.session.CurrentUser;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class LoginView {

    @Inject
    CurrentUser currentUser;

    @Inject
    UserService userService;

    private String username;

    private String password;

    public String validateLogin() {
        if (userService.validateUser(this.username, this.password) != null) {
            this.currentUser.setUser(userService.getUserByName(this.username));
            return "/protected/teams.xhtml";
        } else {
            return "/public/login.xhtml";
        }
    }

    // SETTERS AND GETTERS
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
