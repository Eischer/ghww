package at.eischer.services;

import at.eischer.dao.Repository;
import at.eischer.model.Spiel;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class SpielService extends Repository {

    public List<Spiel> getAllSpielePerGruppe(String gruppe) {
        TypedQuery<Spiel> allSpielePerGruppe = entityManager.createNamedQuery("Spiel.getSpielePerGruppe", Spiel.class);
        allSpielePerGruppe.setParameter("gruppe", gruppe);
        return allSpielePerGruppe.getResultList();
    }
}
