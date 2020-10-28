package com.example.demo.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

public class Trainer {
    private String name;
    private int skillLevel;

    public Trainer(String name, int skillLevel) {
        this.name = name;
        this.skillLevel = skillLevel;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    @Override
    public String toString() {
        return name + " " + skillLevel;
    }
}
