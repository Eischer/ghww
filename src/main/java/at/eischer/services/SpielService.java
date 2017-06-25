package at.eischer.services;

import at.eischer.dao.Repository;
import at.eischer.model.Spiel;
import at.eischer.model.Team;
import at.eischer.view.TeamRank;

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

    public boolean participateOnSpiel(Team team) {
        TypedQuery<Long> spielCountForTeam = entityManager.createNamedQuery("Spiel.participateOnSpiele", Long.class);
        spielCountForTeam.setParameter("team", team);
        return spielCountForTeam.getSingleResult() > 0;
    }
}
