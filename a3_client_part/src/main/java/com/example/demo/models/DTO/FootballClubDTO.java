package com.example.demo.models.DTO;

import com.example.demo.models.FootballPlayer;
import com.example.demo.models.Trainer;

import java.util.List;

public class FootballClubDTO {
    private List<FootballPlayer> footballTeam;

    private String clubName;
    private int budget;
    private Trainer trainer;

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getClubName() {
        return clubName;
    }

    public List<FootballPlayer> getFootballTeam() {
        return footballTeam;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setFootballTeam(List<FootballPlayer> footballTeam) {
        this.footballTeam = footballTeam;
    }
}
