package com.example.transfer_market_service.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class FootballClub {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    private Trainer trainer;

    @OneToMany(targetEntity = FootballPlayer.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private List<FootballPlayer> footballTeam;

    private String clubName;
    private int budget;
    @GeneratedValue
    private int score;

    public FootballClub(String clubName, List<FootballPlayer> footballTeam, Trainer trainer, int budget) {
        this.clubName = clubName;
        this.budget = budget;
        this.footballTeam = footballTeam;
        this.trainer = trainer;
        score = 0;
    }

    public FootballClub() {
    }

    /*
    public FootballClub(FootballClubResponse footballClubResponse) {
        id = UUID.fromString(footballClubResponse.getId());
        clubName = footballClubResponse.getClubName();
        budget = footballClubResponse.getBudget();
        List<FootballPlayerResponse> footballPlayerResponses = footballClubResponse.getFootballTeamList();

        footballTeam = new ArrayList<>();

        for(FootballPlayerResponse footballPlayerResponse:footballPlayerResponses) {
            footballTeam.add(new FootballPlayer(footballPlayerResponse));
        }

        trainer = new Trainer(footballClubResponse.getTrainer());
    }

    public FootballClub(FootballClubRequest footballClubRequest) {
        id = UUID.fromString(footballClubRequest.getId());
        clubName = footballClubRequest.getClubName();
        budget = footballClubRequest.getBudget();
        List<FootballPlayerRequest> footballPlayerRequests = footballClubRequest.getFootballTeamList();

        footballTeam = new ArrayList<>();

        for(FootballPlayerRequest footballPlayerRequest:footballPlayerRequests) {
            footballTeam.add(new FootballPlayer(footballPlayerRequest));
        }

        trainer = new Trainer(footballClubRequest.getTrainer());
    }

    public FootballClubRequest toRequest() {

        List<FootballPlayerRequest> footballPlayerRequests = new ArrayList<>();
        for(FootballPlayer footballPlayer:footballTeam) {
            footballPlayerRequests.add(footballPlayer.toRequest());
        }

        return FootballClubRequest.newBuilder().setClubName(clubName).
                setBudget(budget).setTrainer(trainer.toRequest()).addAllFootballTeam(footballPlayerRequests).build();
    }

    public FootballClubResponse toResponse() {

        List<FootballPlayerResponse> footballPlayerResponses = new ArrayList<>();
        for(FootballPlayer footballPlayer:footballTeam) {
            footballPlayerResponses.add(footballPlayer.toResponse());
        }

        return FootballClubResponse.newBuilder().setClubName(clubName).
                setBudget(budget).setTrainer(trainer.toResponse()).addAllFootballTeam(footballPlayerResponses).build();
    }*/

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
