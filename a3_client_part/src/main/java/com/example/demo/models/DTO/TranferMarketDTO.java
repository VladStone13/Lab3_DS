package com.example.demo.models.DTO;

import com.example.demo.models.FootballPlayer;

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
