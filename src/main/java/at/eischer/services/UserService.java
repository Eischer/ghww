package at.eischer.services;

import at.eischer.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    public String validateUser(String userName, String password) {
        Query validateQuery = entityManager.createNamedQuery("User.validateUser");
        validateQuery.setParameter("username", userName);
        validateQuery.setParameter("password", password);
        List<String> foundUserName =  (List<String>)validateQuery.getResultList();
        if (foundUserName != null && !foundUserName.isEmpty()) {
            return foundUserName.get(0);
        } else {
            return null;
        }
    }

    public User getUserByName (String username) {
        Query geUserByNameQuery = entityManager.createNamedQuery("User.getUserByName").setParameter("username", username);
        return (User) geUserByNameQuery.getSingleResult();
    }
}
