package at.eischer.services;

import at.eischer.dao.Repository;
import at.eischer.model.FinalSpiel;
import at.eischer.model.Spiel;
import at.eischer.model.Team;

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

    public void removeSpiel(Spiel spiel) {
        entityManager.remove(entityManager.contains(spiel) ? spiel : entityManager.merge(spiel));
    }

    public List<Spiel> getAllSpieleWithTeams(List<Team> listOfEqualTeams) {
        TypedQuery<Spiel> allSpieleOfTeams = entityManager.createNamedQuery("Spiel.getSpieleForTeams", Spiel.class);
        allSpieleOfTeams.setParameter("teamList1", listOfEqualTeams).setParameter("teamList2", listOfEqualTeams);
        return allSpieleOfTeams.getResultList();
    }

    public List<FinalSpiel> getAllFinalSpiele() {
        TypedQuery<FinalSpiel> allFinalSpieleQuery = entityManager.createNamedQuery("FinalSpiel.getAllFinalSpiele", FinalSpiel.class);
        List<FinalSpiel> allFinalSpiele = allFinalSpieleQuery.getResultList();
        if (allFinalSpiele.isEmpty()) {
            save(new FinalSpiel(1, "1. Kreuzspiel"));
            save(new FinalSpiel(2, "2. Kreuzspiel"));
            save(new FinalSpiel(3, "Spiel um Platz 11"));
            save(new FinalSpiel(4, "Spiel um Platz 9"));
            save(new FinalSpiel(5, "Spiel um Platz 7"));
            save(new FinalSpiel(6, "Spiel um Platz 5"));
            save(new FinalSpiel(7, "Spiel um Platz 3"));
            save(new FinalSpiel(8, "Spiel um Platz 1"));
            return allFinalSpieleQuery.getResultList();
        } else {
            return allFinalSpiele;
        }
    }

    public boolean participateOnSpiel(Team team) {
        TypedQuery<Long> spielCountForTeam = entityManager.createNamedQuery("Spiel.participateOnSpiele", Long.class);
        spielCountForTeam.setParameter("team", team);
        return spielCountForTeam.getSingleResult() > 0;
    }
}