package com.kolomachine.shifumi;

import java.util.ArrayList;

/**
 * Created by asus pc on 29/10/2017.
 */

public class Tournament {

    private boolean isOn;
    private ArrayList<Player> players;
    private int numberOfRounds;
    private int idActualPlayer;

    public Tournament(boolean isOn){
        this.isOn = isOn;
    }

    public Tournament(int numberOfRounds, String[] names) {
        isOn = true;
        this.numberOfRounds = numberOfRounds;
        players = new ArrayList<>();
        for(int i=0; i<names.length;i++)
            players.add(new Player(names[i]));
        idActualPlayer = 0;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    public int getIdActualPlayer() {
        return idActualPlayer;
    }

    public void increaseIdActualPlayer() {
        this.idActualPlayer ++;
    }
}
