package at.eischer.view;

import at.eischer.services.UserService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class LoginView {

    @Inject
    UserService userService;

    private String username;

    private String password;

    public String validateLogin() {
        if (userService.validateUser(this.username, this.password) != null) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("userName", username);
            return "/protected/createTeam";
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
