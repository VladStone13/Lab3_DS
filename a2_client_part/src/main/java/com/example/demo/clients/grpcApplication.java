package com.example.demo.clients;

import com.example.demo.models.DTO.TransferMarketDTO;
import com.example.demo.models.FootballClub;
import com.example.demo.models.FootballPlayer;
import com.example.demo.models.Trainer;
import com.example.demo.models.TransferMarket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.Empty;
import com.services.grpc.server.footballWorld.FootballWorldServiceGrpc;
import com.services.grpc.server.footballWorld.StringResponse;
import com.services.grpc.server.transferMarket.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

@Component
public class grpcApplication {

    static ManagedChannel channelTrMarket = ManagedChannelBuilder.forAddress("localhost", 9091)
            .usePlaintext().build();

    static TransferMarketServiceGrpc.TransferMarketServiceBlockingStub trMarketStub = TransferMarketServiceGrpc
            .newBlockingStub(channelTrMarket);

    static ManagedChannel channelFootballWorld = ManagedChannelBuilder.forAddress("localhost", 9090)
            .usePlaintext().build();

    static FootballWorldServiceGrpc.FootballWorldServiceBlockingStub footballWorldStub = FootballWorldServiceGrpc
            .newBlockingStub(channelFootballWorld);

    private final static String QUEUE_NAME = "tms-key";

    public static void run(){

        TransferMarket transferMarket = new TransferMarket();

        List<FootballClub> footballClubs = createFootballClubs();

        fillTransferMarket(transferMarket);
        printTransferMarket();

        fillFootballWorld(footballClubs);

        footballWorldStub.playRound(Empty.newBuilder().build());

        StringResponse results = footballWorldStub.getResults(Empty.newBuilder().build());

        System.out.println(results.getAnsw());
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
        List<FootballPlayerRequest> footballPlayerRequests = new ArrayList<>();

        for(FootballPlayer footballPlayer:transferMarket.getPlayersOnTransfer()) {
            footballPlayerRequests.add(footballPlayer.toRequest());
        }

        TransferMarketRequest transferMarketRequest = TransferMarketRequest.newBuilder()
                .addAllTransferMarket(footballPlayerRequests).build();

        Empty response = trMarketStub.fillTransferMarket(transferMarketRequest);
    }
    private static void printTransferMarket() {

        Empty request = Empty.newBuilder().build();

        TransferMarketResponse transferMarket = trMarketStub.getTransferMarket(request);
        for(FootballPlayerResponse footballPlayerResponse:transferMarket.getTransferMarketList()) {
            FootballPlayer footballPlayer = new FootballPlayer(footballPlayerResponse);
            System.out.println(footballPlayer.toString());
        }
    }



    private static void fillFootballWorld(List<FootballClub> footballClubs) {
        for(FootballClub footballClub:footballClubs) {
            Empty response = footballWorldStub.createClub(footballClub.toRequest());
        }
    }

}
