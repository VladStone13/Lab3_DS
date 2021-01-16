package com.example.training_service.controller.grpc;

import com.example.training_service.services.TrainingService;
import com.google.protobuf.Empty;
import com.services.grpc.server.training.IdRequest;
import com.services.grpc.server.training.TrainingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
@AllArgsConstructor
public class TrainingControllerGrpc extends TrainingServiceGrpc.TrainingServiceImplBase {
    private final TrainingService trainingService;


    @Override
    public void train(IdRequest request, StreamObserver<Empty> responseObserver) {
        trainingService.train(UUID.fromString(request.getId()));

        Empty response = Empty.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
