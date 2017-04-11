package at.eischer.services;

import at.eischer.model.SeiderlHistory;
import at.eischer.model.Team;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Stateless
public class MyTimerService {

    @Inject
    TeamService teamService;

    @Inject
    SeiderlHistoryService seiderlHistoryService;

//    @Schedule(second="0", minute="0/1",hour="*", persistent=false)
    public void log () {
        List<Team> teams = teamService.findAllteams();
        for (Team team : teams) {
            seiderlHistoryService.insertSeiderlHistory(new SeiderlHistory(team));
        }
        System.out.println("TIMER SERVICE");
    }
}
