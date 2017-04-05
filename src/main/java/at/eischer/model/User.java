package at.eischer.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TheUser")
@NamedQueries({
        @NamedQuery(name = "User.validateUser",
                query = "SELECT username FROM User u WHERE u.username=:username AND u.password=:password"),
        @NamedQuery(name = "User.getUserByName", query = "SELECT u FROM User u WHERE u.username = :username")
})
public class User{

    @Id
    private int id;

    private String username;

    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
