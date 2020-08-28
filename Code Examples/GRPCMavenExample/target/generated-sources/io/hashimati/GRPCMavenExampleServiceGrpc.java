package io.hashimati;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.30.2)",
    comments = "Source: GRPCMavenExample.proto")
public final class GRPCMavenExampleServiceGrpc {

  private GRPCMavenExampleServiceGrpc() {}

  public static final String SERVICE_NAME = "io.hashimati.GRPCMavenExampleService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.hashimati.GRPCMavenExampleRequest,
      io.hashimati.GRPCMavenExampleReply> getSendMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "send",
      requestType = io.hashimati.GRPCMavenExampleRequest.class,
      responseType = io.hashimati.GRPCMavenExampleReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.hashimati.GRPCMavenExampleRequest,
      io.hashimati.GRPCMavenExampleReply> getSendMethod() {
    io.grpc.MethodDescriptor<io.hashimati.GRPCMavenExampleRequest, io.hashimati.GRPCMavenExampleReply> getSendMethod;
    if ((getSendMethod = GRPCMavenExampleServiceGrpc.getSendMethod) == null) {
      synchronized (GRPCMavenExampleServiceGrpc.class) {
        if ((getSendMethod = GRPCMavenExampleServiceGrpc.getSendMethod) == null) {
          GRPCMavenExampleServiceGrpc.getSendMethod = getSendMethod =
              io.grpc.MethodDescriptor.<io.hashimati.GRPCMavenExampleRequest, io.hashimati.GRPCMavenExampleReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "send"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.hashimati.GRPCMavenExampleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.hashimati.GRPCMavenExampleReply.getDefaultInstance()))
              .setSchemaDescriptor(new GRPCMavenExampleServiceMethodDescriptorSupplier("send"))
              .build();
        }
      }
    }
    return getSendMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GRPCMavenExampleServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GRPCMavenExampleServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GRPCMavenExampleServiceStub>() {
        @java.lang.Override
        public GRPCMavenExampleServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GRPCMavenExampleServiceStub(channel, callOptions);
        }
      };
    return GRPCMavenExampleServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GRPCMavenExampleServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GRPCMavenExampleServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GRPCMavenExampleServiceBlockingStub>() {
        @java.lang.Override
        public GRPCMavenExampleServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GRPCMavenExampleServiceBlockingStub(channel, callOptions);
        }
      };
    return GRPCMavenExampleServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GRPCMavenExampleServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GRPCMavenExampleServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GRPCMavenExampleServiceFutureStub>() {
        @java.lang.Override
        public GRPCMavenExampleServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GRPCMavenExampleServiceFutureStub(channel, callOptions);
        }
      };
    return GRPCMavenExampleServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class GRPCMavenExampleServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void send(io.hashimati.GRPCMavenExampleRequest request,
        io.grpc.stub.StreamObserver<io.hashimati.GRPCMavenExampleReply> responseObserver) {
      asyncUnimplementedUnaryCall(getSendMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSendMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.hashimati.GRPCMavenExampleRequest,
                io.hashimati.GRPCMavenExampleReply>(
                  this, METHODID_SEND)))
          .build();
    }
  }

  /**
   */
  public static final class GRPCMavenExampleServiceStub extends io.grpc.stub.AbstractAsyncStub<GRPCMavenExampleServiceStub> {
    private GRPCMavenExampleServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GRPCMavenExampleServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GRPCMavenExampleServiceStub(channel, callOptions);
    }

    /**
     */
    public void send(io.hashimati.GRPCMavenExampleRequest request,
        io.grpc.stub.StreamObserver<io.hashimati.GRPCMavenExampleReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSendMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class GRPCMavenExampleServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<GRPCMavenExampleServiceBlockingStub> {
    private GRPCMavenExampleServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GRPCMavenExampleServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GRPCMavenExampleServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public io.hashimati.GRPCMavenExampleReply send(io.hashimati.GRPCMavenExampleRequest request) {
      return blockingUnaryCall(
          getChannel(), getSendMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class GRPCMavenExampleServiceFutureStub extends io.grpc.stub.AbstractFutureStub<GRPCMavenExampleServiceFutureStub> {
    private GRPCMavenExampleServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GRPCMavenExampleServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GRPCMavenExampleServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.hashimati.GRPCMavenExampleReply> send(
        io.hashimati.GRPCMavenExampleRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSendMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GRPCMavenExampleServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(GRPCMavenExampleServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND:
          serviceImpl.send((io.hashimati.GRPCMavenExampleRequest) request,
              (io.grpc.stub.StreamObserver<io.hashimati.GRPCMavenExampleReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class GRPCMavenExampleServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GRPCMavenExampleServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.hashimati.GRPCMavenExample.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("GRPCMavenExampleService");
    }
  }

  private static final class GRPCMavenExampleServiceFileDescriptorSupplier
      extends GRPCMavenExampleServiceBaseDescriptorSupplier {
    GRPCMavenExampleServiceFileDescriptorSupplier() {}
  }

  private static final class GRPCMavenExampleServiceMethodDescriptorSupplier
      extends GRPCMavenExampleServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    GRPCMavenExampleServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (GRPCMavenExampleServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GRPCMavenExampleServiceFileDescriptorSupplier())
              .addMethod(getSendMethod())
              .build();
        }
      }
    }
    return result;
  }
}
