package com.example.qualification_course.controller.grpc;

import com.example.qualification_course.services.QualificationCourseService;
import com.google.protobuf.Empty;
import com.services.grpc.server.qualificationCourse.IdRequest;
import com.services.grpc.server.qualificationCourse.QualificationCourseServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
@AllArgsConstructor
public class QualificationCourseControllerGrpc extends QualificationCourseServiceGrpc.QualificationCourseServiceImplBase {
    QualificationCourseService qualificationCourseService;

    @Override
    public void levelUpTrainer(IdRequest request, StreamObserver<Empty> responseObserver) {
        qualificationCourseService.levelUpTrainer(UUID.fromString(request.getId()));

        Empty response = Empty.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
