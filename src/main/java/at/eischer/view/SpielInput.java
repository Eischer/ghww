package at.eischer.view;

import at.eischer.model.Team;

import java.util.ArrayList;
import java.util.List;

public class SpielInput {

    private int hour;

    private int minute;

    private Team homeTeam;

    private Team awayTeam;

    private List<Integer> hours;

    private List<Integer> minutes;

    public SpielInput () {
        this.hours = new ArrayList<>();
        for (int i=0; i<24; i++) {
            this.hours.add(i);
        }
        this.minutes = new ArrayList<>();
        for (int i=0; i<60; i++) {
            this.minutes.add(i);
        }
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public List<Integer> getHours() {
        return hours;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public List<Integer> getMinutes() {
        return minutes;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }
}