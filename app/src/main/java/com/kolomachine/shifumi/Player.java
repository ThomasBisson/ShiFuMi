package com.kolomachine.shifumi;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by asus pc on 29/10/2017.
 */

public class Player implements Serializable {


    public enum Result {
        TIE(0),
        WIN(1),
        LOOSE(-1);

        private int valeur;

        Result(int valeur) {
            this.valeur = valeur;
        }

        public int getValeur() {return valeur; }
    }

    private ArrayList<Result> scores;
    private String name;

    public Player(String name) {
        this.name = name;
        scores = new ArrayList<>();
    }

    public ArrayList<Result> getScores() {
        return scores;
    }

    public void addScore(Result score) {
        this.scores.add(score);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
