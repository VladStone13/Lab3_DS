package com.example.qualification_course.models.DTO;

import com.example.qualification_course.models.FootballPlayer;

import java.util.List;

public class TranferMarketDTO {
    List<FootballPlayer> footballPlayerList;

    public List<FootballPlayer> getFootballPlayerList() {
        return footballPlayerList;
    }

    public void setFootballPlayerList(List<FootballPlayer> footballPlayerList) {
        this.footballPlayerList = footballPlayerList;
    }
}
