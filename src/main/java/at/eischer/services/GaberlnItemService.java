package at.eischer.services;

import at.eischer.dao.Repository;
import at.eischer.model.GaberlnItem;

import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class GaberlnItemService extends Repository {

    public List<GaberlnItem> findAll () {
        return entityManager.createNamedQuery("GaberlnItem.allGaberlnItems", GaberlnItem.class).getResultList();
    }

    public Long getJackpot() {
        return entityManager.createNamedQuery("GaberlnItem.calculateJackpot", Long.class).getSingleResult();
    }
}
