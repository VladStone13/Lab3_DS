package com.example.demo.models;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class FootballClub {
    private Trainer trainer;

    private List<FootballPlayer> footballTeam;

    private String clubName;
    private int budget;

    public FootballClub(String clubName, Trainer trainer, List<FootballPlayer> footballTeam, int budget) {
        this.clubName = clubName;
        this.footballTeam = footballTeam;
        this.budget = budget;
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

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setFootballTeam(List<FootballPlayer> footballTeam) {
        this.footballTeam = footballTeam;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public List<FootballPlayer> getFootballTeam() {
        return footballTeam;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void addPlayerToFootballTeam(FootballPlayer footballPlayer) {
        footballTeam.add(footballPlayer);
    }

    public void deletePlayerToFootballTeam(FootballPlayer footballPlayer) {
        footballTeam.remove(footballPlayer);
    }

    public String showFootballClub() {
        String trainerToString = "Trainer\n" + trainer.toString();

        String listPlayersInClub = "\nPlayers\n";
        for (FootballPlayer footballPlayer: footballTeam) {
            listPlayersInClub += footballPlayer.toString();
            listPlayersInClub += "\n";
        }

        return trainerToString+listPlayersInClub;
    }

    public int calculateStrongTeam() {
        int sumRatingScore = 0;

        for(FootballPlayer footballPlayer:footballTeam) {
            sumRatingScore += footballPlayer.getRatingScore();
        }

        return sumRatingScore/footballTeam.size();
    }
}
