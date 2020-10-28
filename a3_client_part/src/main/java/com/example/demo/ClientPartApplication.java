package com.example.demo;

import com.example.demo.models.DTO.FillFootballPlayerDTO;
import com.example.demo.models.DTO.FootballClubDTO;
import com.example.demo.models.TransferMarket;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.example.demo.models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

@SpringBootApplication
public class ClientPartApplication {
    private static final String URL1 = "http://localhost:8080";
    private static final String URL2 = "http://localhost:8081";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders headers = new HttpHeaders();
    private static final HttpEntity<Object> headersEntity = new HttpEntity<>(headers);

    public static void main(String[] args) {
        headers.setContentType(MediaType.APPLICATION_JSON);

        TransferMarket transferMarket = new TransferMarket();

        List<FootballClub> footballClubs = createFootballClubs();

        fillTransferMarket(transferMarket);
        printTransferMarket();

        fillFootballWorld(footballClubs);

        ResponseEntity<Void> response2 = restTemplate
                .exchange(URL1 + "/playRound", HttpMethod.GET, headersEntity, Void.class);

        ResponseEntity<String> response3 = restTemplate
                .exchange(URL1 + "/results", HttpMethod.GET, headersEntity, String.class);

        System.out.println(response3.getBody());
    }

    private static List<FootballClub> createFootballClubs() {
        Trainer trainerDnepr = new Trainer("Cohen", 3);
        Trainer trainerDynamo = new Trainer("Hughes", 5);
        Trainer trainerShakhtar = new Trainer("Sandoval",4);

        FootballPlayer footballPlayer1 = new FootballPlayer("Aleg", 22, 80, "GK");
        FootballPlayer footballPlayer2 = new FootballPlayer("Vlad", 19, 88, "DF");
        FootballPlayer footballPlayer3 = new FootballPlayer("Max", 25, 73, "CM");
        FootballPlayer footballPlayer4 = new FootballPlayer("Neymar", 28, 67, "ST");
        List<FootballPlayer> footballTeamDnepr = new ArrayList<FootballPlayer>();
        footballTeamDnepr.add(footballPlayer1);
        footballTeamDnepr.add(footballPlayer2);
        footballTeamDnepr.add(footballPlayer3);
        footballTeamDnepr.add(footballPlayer4);

        FootballClub footballClubDnepr = new FootballClub("Dnepr", trainerDnepr, footballTeamDnepr, 100000);

        List<FootballPlayer> footballTeamDynamo = footballTeamDnepr;
        footballTeamDynamo.get(0).setName("Alexey");
        footballTeamDynamo.get(1).setName("Sanya");
        footballTeamDynamo.get(2).setName("Rito");
        footballTeamDynamo.get(3).setName("Malts");

        FootballClub footballClubDynamo = new FootballClub("Dynamo", trainerDynamo, footballTeamDynamo, 200000);

        List<FootballPlayer> foootballClubShacktar = footballTeamDnepr;
        foootballClubShacktar.get(0).setName("Bracut");
        foootballClubShacktar.get(1).setName("Vers");
        foootballClubShacktar.get(2).setName("Mrok");
        foootballClubShacktar.get(3).setName("Lord");
        FootballClub footballClubShacktar = new FootballClub("Shakhtar", trainerShakhtar, foootballClubShacktar, 300000);
        List<FootballClub> footballClubs = new ArrayList<>();
        footballClubs.add(footballClubDnepr);
        footballClubs.add(footballClubDynamo);
        footballClubs.add(footballClubShacktar);

        return footballClubs;
    }

    private static void fillTransferMarket(TransferMarket transferMarket) {
        FillFootballPlayerDTO footballPlayersDTO = new FillFootballPlayerDTO();
        footballPlayersDTO.setFootballPlayerList(transferMarket.getPlayersOnTransfer());
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String footballPlayersJson = gson.toJson(footballPlayersDTO);

        HttpEntity<String> deliverJson = new HttpEntity<>(footballPlayersJson, headers);

        ResponseEntity<Void> response1 = restTemplate
                .exchange(URL2 + "/transferMarkets/fill", HttpMethod.POST, deliverJson, Void.class);

        System.out.println(response1.toString());
    }

    private static void printTransferMarket() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<FillFootballPlayerDTO> response2 = restTemplate
                .exchange(URL2 + "/transferMarket", HttpMethod.GET, headersEntity, FillFootballPlayerDTO.class);
        List<FootballPlayer> footballPlayerList = response2.getBody().getFootballPlayerList();

        for(FootballPlayer footballPlayer:footballPlayerList) {
            System.out.println(footballPlayer.toString());
        }
    }

    private static void fillFootballWorld(List<FootballClub> footballClubs) {
        for(FootballClub footballClub:footballClubs) {
            FootballClubDTO footballClubDTO= new FootballClubDTO();
            footballClubDTO.setBudget(footballClub.getBudget());
            footballClubDTO.setClubName(footballClub.getClubName());
            footballClubDTO.setFootballTeam(footballClub.getFootballTeam());
            footballClubDTO.setTrainer(footballClub.getTrainer());

            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String footballClubJson = gson.toJson(footballClubDTO);

            HttpEntity<String> deliverJson = new HttpEntity<>(footballClubJson, headers);

            ResponseEntity<Void> response1 = restTemplate
                    .exchange(URL1 + "/footballWorld/createClub", HttpMethod.POST, deliverJson, Void.class);

            System.out.println(response1.toString());
        }
    }
}
