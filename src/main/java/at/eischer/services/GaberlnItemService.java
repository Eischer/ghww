package at.eischer.services;

import at.eischer.dao.Repository;
import at.eischer.model.GaberlnItem;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class GaberlnItemService extends Repository {

    public List<GaberlnItem> findAll () {
        return entityManager.createNamedQuery("GaberlnItem.allGaberlnItems", GaberlnItem.class).getResultList();
    }

    public void remove(GaberlnItem gaberlnItem) {
        entityManager.remove(entityManager.contains(gaberlnItem) ? gaberlnItem : entityManager.merge(gaberlnItem));
    }
}