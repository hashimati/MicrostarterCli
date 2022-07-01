package io.hashimati.clients;


import io.grpc.ManagedChannel;
import io.hashimati.FruitServiceGrpc;
import io.micronaut.context.annotation.Factory;
import io.micronaut.grpc.annotation.GrpcChannel;
import jakarta.inject.Singleton;

@Factory
public class FruitClient {


    @Singleton
    FruitServiceGrpc.FruitServiceStub fruitServiceStub(@GrpcChannel("http://${my.server}:${my.port}")

                                                       ManagedChannel managedChannel){

        return
                FruitServiceGrpc.newStub(managedChannel);
    }
}
