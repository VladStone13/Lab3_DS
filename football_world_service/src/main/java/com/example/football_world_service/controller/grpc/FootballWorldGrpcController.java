package com.example.football_world_service.controller.grpc;

import com.example.football_world_service.models.FootballClub;
import com.example.football_world_service.models.Trainer;
import com.example.football_world_service.service.FootballWorldService;
import com.google.protobuf.Empty;
import com.services.grpc.server.footballWorld.*;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
@AllArgsConstructor
public class FootballWorldGrpcController extends FootballWorldServiceGrpc.FootballWorldServiceImplBase {
    private final FootballWorldService footballWorldService;

    @Override
    public void playRound(Empty request, StreamObserver<Empty> responseObserver) {
        footballWorldService.playRound();
        Empty response = Empty.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void buyPlayer(IdRequest request, StreamObserver<FootballClubResponse> responseObserver) {
        footballWorldService.buyPlayer(UUID.fromString(request.getRequest()));
        FootballClub footballClub = footballWorldService.getFootballClub(UUID.fromString(request.getRequest()));

        FootballClubResponse response = footballClub.toResponse();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void sellPlayer(IdRequest request, StreamObserver<FootballClubResponse> responseObserver) {
        footballWorldService.sellPlayer(UUID.fromString(request.getRequest()));
        FootballClub footballClub = footballWorldService.getFootballClub(UUID.fromString(request.getRequest()));

        FootballClubResponse response = footballClub.toResponse();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createClub(FootballClubRequest request, StreamObserver<Empty> responseObserver) {
        footballWorldService.createClub(new FootballClub(request));
        Empty response = Empty.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getTrainer(IdRequest request, StreamObserver<TrainerResponse> responseObserver) {
        Trainer trainer = footballWorldService.getTrainer(UUID.fromString(request.getRequest()));

        TrainerResponse response = trainer.toResponse();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getFootballClub(IdRequest request, StreamObserver<FootballClubResponse> responseObserver) {
        FootballClub footballClub = footballWorldService.getFootballClub(UUID.fromString(request.getRequest()));

        FootballClubResponse response = footballClub.toResponse();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getResults(Empty request, StreamObserver<StringResponse> responseObserver) {
        StringResponse response = StringResponse.newBuilder().setAnsw(footballWorldService.results()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
