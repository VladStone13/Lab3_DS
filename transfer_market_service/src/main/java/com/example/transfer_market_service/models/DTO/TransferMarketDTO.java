package com.example.transfer_market_service.models.DTO;

import com.example.transfer_market_service.models.FootballPlayer;

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
