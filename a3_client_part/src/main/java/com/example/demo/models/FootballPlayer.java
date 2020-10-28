package com.example.demo.models;

import javax.persistence.*;
import java.util.UUID;


public class FootballPlayer {

    private String name;
    private int age;
    private int ratingScore;
    private String position;


    public FootballPlayer(String name, int age, int ratingScore, String position) {
        this.age = age;
        this.name = name;
        this.ratingScore = ratingScore;
        this.position = position;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FootballPlayer() {}

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(int ratingScore) {
        this.ratingScore = ratingScore;
    }

    public int calculateCost() {
        return 5000*(45-age)*ratingScore;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return name + " "
                + ratingScore + " "
                + position + " "
                + age;
    }
}
