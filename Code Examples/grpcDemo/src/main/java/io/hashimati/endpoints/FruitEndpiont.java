package io.hashimati.endpoints;


import io.grpc.stub.StreamObserver;
import io.hashimati.FruitProto;
import io.hashimati.FruitServiceGrpc;
import jakarta.inject.Singleton;

@Singleton
@SuppressWarnings("unused")
public class FruitEndpiont extends FruitServiceGrpc.FruitServiceImplBase {
    @Override
    public void send(FruitProto request, StreamObserver<FruitProto> responseObserver) {
        responseObserver.onNext(request);
        responseObserver.onCompleted();;
    }
}
