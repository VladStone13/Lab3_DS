package com.example.transfer_market_service.controller.grpc;


import com.example.transfer_market_service.models.TransferMarket;
import com.example.transfer_market_service.services.TransferMarketService;
import com.example.transfer_market_service.models.FootballPlayer;
import com.google.protobuf.Empty;
import com.services.grpc.server.transferMarket.*;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

@GrpcService
@AllArgsConstructor
public class TransferMarketControllerGrpc extends TransferMarketServiceGrpc.TransferMarketServiceImplBase {
    private final TransferMarketService transferMarketService;

    @Override
    public void fillTransferMarket(TransferMarketRequest request, StreamObserver<Empty> responseObserver) {
        List<FootballPlayerRequest> transferMarketList = request.getTransferMarketList();
        List<FootballPlayer> transferMarketPlayers = new ArrayList<>();

        for(FootballPlayerRequest footballPlayerRequest:transferMarketList) {
            transferMarketPlayers.add(new FootballPlayer(footballPlayerRequest));
        }

        transferMarketService.create(transferMarketPlayers);
        Empty response = Empty.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getTransferMarket(Empty request, StreamObserver<TransferMarketResponse> responseObserver) {
        List<FootballPlayer> transferMarket = transferMarketService.getAll().getPlayersOnTransfer();
        List<FootballPlayerResponse> footballPlayerResponses = new ArrayList<>();

        for(FootballPlayer footballPlayer:transferMarket) {
            footballPlayerResponses.add(footballPlayer.toResponse());
        }

        TransferMarketResponse response = TransferMarketResponse.newBuilder().
                addAllTransferMarket(footballPlayerResponses).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addPlayer(FootballPlayerRequest request, StreamObserver<Empty> responseObserver) {
        FootballPlayer footballPlayer = new FootballPlayer(request);
        transferMarketService.add(footballPlayer);
        Empty response = Empty.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deletePlayer(FootballPlayerRequest request, StreamObserver<Empty> responseObserver) {
        FootballPlayer footballPlayer = new FootballPlayer(request);
        transferMarketService.delete(footballPlayer);

        Empty response = Empty.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
