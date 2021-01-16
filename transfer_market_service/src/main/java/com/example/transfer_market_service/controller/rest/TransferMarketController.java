package com.example.transfer_market_service.controller.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.transfer_market_service.models.DTO.TransferMarketDTO;
import com.example.transfer_market_service.models.DTO.FootballPlayerDTO;
import com.example.transfer_market_service.models.FootballPlayer;
import com.example.transfer_market_service.services.TransferMarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransferMarketController {
    @Autowired
    final private TransferMarketService transferMarketService;

    @Autowired
    private final RabbitTemplate rabbitTemplate;


    @PostMapping("transferMarkets/fill")
    public ResponseEntity<Void> create(@RequestBody String deliverJson) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        TransferMarketDTO footballPlayersDto = gson.fromJson(deliverJson, TransferMarketDTO.class);
        List<FootballPlayer> footballPlayers = footballPlayersDto.getFootballPlayerList();

        transferMarketService.create(footballPlayers);
        return ResponseEntity.ok().build();
    }

    @PostMapping("rmq")
    public ResponseEntity<Void> createRmq(@RequestBody String deliverJson) {
        rabbitTemplate.convertAndSend("exchange","tms_key",deliverJson);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/transferMarket")
    public @ResponseBody
    TransferMarketDTO allTr() {
        TransferMarketDTO footballPlayers = new TransferMarketDTO();
        footballPlayers.setFootballPlayerList(
                transferMarketService.getAll().getPlayersOnTransfer());
        return footballPlayers;
    }

    @PostMapping("/transferMarket/add")
    public ResponseEntity<Void> addPlayer(@RequestBody String deliverJson) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        FootballPlayerDTO player = gson.fromJson(deliverJson, FootballPlayerDTO.class);

        FootballPlayer footballPlayer = new FootballPlayer(player.getName(),
                player.getRatingScore(), player.getRatingScore(), player.getPosition());

        transferMarketService.add(footballPlayer);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/transferMarket/delete")
    public ResponseEntity<Void> deletePlayer(@RequestBody String deliverJson) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        FootballPlayerDTO player = gson.fromJson(deliverJson, FootballPlayerDTO.class);

        FootballPlayer footballPlayer = new FootballPlayer(player.getName(),
                player.getRatingScore(), player.getRatingScore(), player.getPosition());

        transferMarketService.delete(footballPlayer);
        return ResponseEntity.noContent().build();
    }

}
