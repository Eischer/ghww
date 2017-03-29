package at.eischer.services;

import javax.ejb.Schedule;
import javax.ejb.Stateless;

@Stateless
public class MyTimerService {
    @Schedule(second="0", minute="0/1",hour="*", persistent=false)
    public void log () {
        System.out.println("TIMER SERVICE");
    }
}
