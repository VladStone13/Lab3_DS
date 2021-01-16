package com.example.football_world_service.controller.rest;

import com.example.football_world_service.models.Trainer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.football_world_service.models.DTO.FootballClubDTO;
import com.example.football_world_service.models.FootballClub;
import com.example.football_world_service.service.FootballWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class FootballWorldController {
    final private FootballWorldService footballWorldService;

    @Autowired
    public FootballWorldController(FootballWorldService footballWorldService) {
        this.footballWorldService = footballWorldService;
    }

    @GetMapping("/playRound")
    public ResponseEntity<Void> playRound() {
        footballWorldService.playRound();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/buy/{id}")
    public ResponseEntity<FootballClub> buy(@PathVariable UUID id) {
        footballWorldService.buyPlayer(id);

        return ResponseEntity.ok(footballWorldService.getFootballClub(id));
    }

    @PostMapping("/sell/{id}")
    public FootballClub sell(@PathVariable UUID id) {
        footballWorldService.sellPlayer(id);

        return footballWorldService.getFootballClub(id);
    }

    @PostMapping("/footballWorld/createClub")
    public ResponseEntity<Void> createClub(@RequestBody String deliverJson) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        FootballClubDTO footballPlayersDto = gson.fromJson(deliverJson, FootballClubDTO.class);

        footballWorldService.createClub(new FootballClub(footballPlayersDto.getClubName(),
                footballPlayersDto.getFootballTeam(), footballPlayersDto.getTrainer(),
                footballPlayersDto.getBudget()));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/footballWorld/trainer/{id}")
    public Trainer getTrainer(@PathVariable UUID id){
        return footballWorldService.getTrainer(id);
    }

    @GetMapping("/footballWorld/footballClub/{id}")
    public FootballClub getFootballClub(@PathVariable UUID id) {
        return footballWorldService.getFootballClub(id);
    }

    @GetMapping("/results")
    public String printResults() {
        return footballWorldService.results();
    }
}