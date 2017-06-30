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

    private String spielDescription;

    private List<String> allSpielDescriptions;

    public SpielInput () {
        this.hours = new ArrayList<>();
        for (int i=0; i<24; i++) {
            this.hours.add(i);
        }
        this.minutes = new ArrayList<>();
        for (int i=0; i<60; i++) {
            this.minutes.add(i);
        }
        this.allSpielDescriptions = new ArrayList<>();
        this.allSpielDescriptions.add("Spiel um Platz 11");
        this.allSpielDescriptions.add("Spiel um Platz 9");
        this.allSpielDescriptions.add("Spiel um Platz 7");
        this.allSpielDescriptions.add("Spiel um Platz 5");
        this.allSpielDescriptions.add("Spiel um Platz 3 (Kleines Finale)");
        this.allSpielDescriptions.add("Spiel um Platz 1 (Finale)");
        this.allSpielDescriptions.add("Kreuzspiel 1");
        this.allSpielDescriptions.add("Kreuzspiel 2");
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

    public String getSpielDescription() {
        return spielDescription;
    }

    public void setSpielDescription(String spielDescription) {
        this.spielDescription = spielDescription;
    }

    public List<String> getAllSpielDescriptions() {
        return allSpielDescriptions;
    }

    public void setAllSpielDescriptions(List<String> allSpielDescriptions) {
        this.allSpielDescriptions = allSpielDescriptions;
    }
}