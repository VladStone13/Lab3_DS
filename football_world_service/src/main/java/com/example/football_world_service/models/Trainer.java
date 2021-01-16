package com.example.football_world_service.models;


import com.services.grpc.server.footballWorld.TrainerRequest;
import com.services.grpc.server.footballWorld.TrainerResponse;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private int skillLevel;

    public Trainer() {
    }

    public Trainer(String name, int skillLevel) {
        this.name = name;
        this.skillLevel = skillLevel;
    }

    public Trainer(TrainerRequest trainerRequest) {
        id = UUID.fromString(trainerRequest.getId());
        name = trainerRequest.getName();
        skillLevel = trainerRequest.getSkillLevel();
    }

    public Trainer(TrainerResponse trainerResponse) {
        id = UUID.fromString(trainerResponse.getId());
        name = trainerResponse.getName();
        skillLevel = trainerResponse.getSkillLevel();
    }

    public TrainerRequest toRequest() {
        return TrainerRequest.newBuilder().
                setId(id.toString()).
                setSkillLevel(skillLevel)
                .setName(name).build();
    }

    public TrainerResponse toResponse() {
        return TrainerResponse.newBuilder().
                setId(id.toString()).
                setSkillLevel(skillLevel)
                .setName(name).build();
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    @Override
    public String toString() {
        return name + " " + skillLevel;
    }
}
