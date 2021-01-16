package com.example.transfer_market_service.models;


import com.services.grpc.server.transferMarket.FootballPlayerRequest;
import com.services.grpc.server.transferMarket.FootballPlayerResponse;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class FootballPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

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

    public FootballPlayer(FootballPlayerResponse footballPlayerResponse) {
        name = footballPlayerResponse.getName();
        id = UUID.fromString(footballPlayerResponse.getId());
        age = footballPlayerResponse.getAge();
        ratingScore = footballPlayerResponse.getRatingScore();
        position = footballPlayerResponse.getPosition();
    }

    public FootballPlayer(FootballPlayerRequest footballPlayerResponse) {
        name = footballPlayerResponse.getName();
        id = UUID.fromString(footballPlayerResponse.getId());
        age = footballPlayerResponse.getAge();
        ratingScore = footballPlayerResponse.getRatingScore();
        position = footballPlayerResponse.getPosition();
    }

    public FootballPlayerRequest toRequest() {
        return FootballPlayerRequest.newBuilder().
                setAge(age).
                setId(id.toString()).
                setRatingScore(ratingScore).
                setName(name).
                setPosition(position).build();
    }

    public FootballPlayerResponse toResponse() {
        return FootballPlayerResponse.newBuilder().
                setAge(age).
                setId(id.toString()).
                setRatingScore(ratingScore).
                setName(name).
                setPosition(position).build();
    }

    public FootballPlayer() {}

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

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
