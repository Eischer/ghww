package at.eischer.services;

import at.eischer.model.SeiderlHistory;
import at.eischer.model.Team;

import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@Stateless
public class MyTimerService {

    @Inject
    TeamService teamService;

    @Inject
    SeiderlHistoryService seiderlHistoryService;

    @Schedule(hour="9-19", persistent=false)
    public void log () {
        List<Team> teams = teamService.findAllteams();
        for (Team team : teams) {
            seiderlHistoryService.insertSeiderlHistory(new SeiderlHistory(team));
        }
    }

    @Schedule(second = "0", minute = "0", hour = "0")
    public void deleteSeidlHistoryAndResetCounter() {
        seiderlHistoryService.deleteHistory();
        teamService.resetSeidlCounter();
    }
}
