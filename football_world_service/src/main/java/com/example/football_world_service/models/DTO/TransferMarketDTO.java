package com.example.football_world_service.models.DTO;

import com.example.football_world_service.models.FootballPlayer;

import java.util.List;

public class TransferMarketDTO {
    List<FootballPlayer> footballPlayerList;

    public List<FootballPlayer> getFootballPlayerList() {
        return footballPlayerList;
    }

    public void setFootballPlayerList(List<FootballPlayer> footballPlayerList) {
        this.footballPlayerList = footballPlayerList;
    }
}
