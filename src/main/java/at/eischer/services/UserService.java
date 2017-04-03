package at.eischer.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
}
