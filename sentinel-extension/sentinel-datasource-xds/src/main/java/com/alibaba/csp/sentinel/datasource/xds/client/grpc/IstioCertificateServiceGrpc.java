package com.alibaba.csp.sentinel.datasource.xds.client.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Service for managing certificates issued by the CA.
 * </pre>
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class IstioCertificateServiceGrpc {

  private IstioCertificateServiceGrpc() {}

  public static final String SERVICE_NAME = "com.alibaba.csp.sentinel.datasource.xds.client.grpc.IstioCertificateService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<IstioCertificateRequest,
      IstioCertificateResponse> getCreateCertificateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateCertificate",
      requestType = IstioCertificateRequest.class,
      responseType = IstioCertificateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<IstioCertificateRequest,
      IstioCertificateResponse> getCreateCertificateMethod() {
    io.grpc.MethodDescriptor<IstioCertificateRequest, IstioCertificateResponse> getCreateCertificateMethod;
    if ((getCreateCertificateMethod = IstioCertificateServiceGrpc.getCreateCertificateMethod) == null) {
      synchronized (IstioCertificateServiceGrpc.class) {
        if ((getCreateCertificateMethod = IstioCertificateServiceGrpc.getCreateCertificateMethod) == null) {
          IstioCertificateServiceGrpc.getCreateCertificateMethod = getCreateCertificateMethod =
              io.grpc.MethodDescriptor.<IstioCertificateRequest, IstioCertificateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateCertificate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  IstioCertificateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  IstioCertificateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new IstioCertificateServiceMethodDescriptorSupplier("CreateCertificate"))
              .build();
        }
      }
    }
    return getCreateCertificateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static IstioCertificateServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IstioCertificateServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IstioCertificateServiceStub>() {
        @Override
        public IstioCertificateServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IstioCertificateServiceStub(channel, callOptions);
        }
      };
    return IstioCertificateServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static IstioCertificateServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IstioCertificateServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IstioCertificateServiceBlockingV2Stub>() {
        @Override
        public IstioCertificateServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IstioCertificateServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return IstioCertificateServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static IstioCertificateServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IstioCertificateServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IstioCertificateServiceBlockingStub>() {
        @Override
        public IstioCertificateServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IstioCertificateServiceBlockingStub(channel, callOptions);
        }
      };
    return IstioCertificateServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static IstioCertificateServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IstioCertificateServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IstioCertificateServiceFutureStub>() {
        @Override
        public IstioCertificateServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IstioCertificateServiceFutureStub(channel, callOptions);
        }
      };
    return IstioCertificateServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Service for managing certificates issued by the CA.
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Using provided CSR, returns a signed certificate.
     * </pre>
     */
    default void createCertificate(IstioCertificateRequest request,
        io.grpc.stub.StreamObserver<IstioCertificateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateCertificateMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service IstioCertificateService.
   * <pre>
   * Service for managing certificates issued by the CA.
   * </pre>
   */
  public static abstract class IstioCertificateServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return IstioCertificateServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service IstioCertificateService.
   * <pre>
   * Service for managing certificates issued by the CA.
   * </pre>
   */
  public static final class IstioCertificateServiceStub
      extends io.grpc.stub.AbstractAsyncStub<IstioCertificateServiceStub> {
    private IstioCertificateServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected IstioCertificateServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IstioCertificateServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Using provided CSR, returns a signed certificate.
     * </pre>
     */
    public void createCertificate(IstioCertificateRequest request,
        io.grpc.stub.StreamObserver<IstioCertificateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateCertificateMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service IstioCertificateService.
   * <pre>
   * Service for managing certificates issued by the CA.
   * </pre>
   */
  public static final class IstioCertificateServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<IstioCertificateServiceBlockingV2Stub> {
    private IstioCertificateServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected IstioCertificateServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IstioCertificateServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     * <pre>
     * Using provided CSR, returns a signed certificate.
     * </pre>
     */
    public IstioCertificateResponse createCertificate(IstioCertificateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateCertificateMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service IstioCertificateService.
   * <pre>
   * Service for managing certificates issued by the CA.
   * </pre>
   */
  public static final class IstioCertificateServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<IstioCertificateServiceBlockingStub> {
    private IstioCertificateServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected IstioCertificateServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IstioCertificateServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Using provided CSR, returns a signed certificate.
     * </pre>
     */
    public IstioCertificateResponse createCertificate(IstioCertificateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateCertificateMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service IstioCertificateService.
   * <pre>
   * Service for managing certificates issued by the CA.
   * </pre>
   */
  public static final class IstioCertificateServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<IstioCertificateServiceFutureStub> {
    private IstioCertificateServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected IstioCertificateServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IstioCertificateServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Using provided CSR, returns a signed certificate.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<IstioCertificateResponse> createCertificate(
        IstioCertificateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateCertificateMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_CERTIFICATE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_CERTIFICATE:
          serviceImpl.createCertificate((IstioCertificateRequest) request,
              (io.grpc.stub.StreamObserver<IstioCertificateResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCreateCertificateMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              IstioCertificateRequest,
              IstioCertificateResponse>(
                service, METHODID_CREATE_CERTIFICATE)))
        .build();
  }

  private static abstract class IstioCertificateServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    IstioCertificateServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Ca.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("IstioCertificateService");
    }
  }

  private static final class IstioCertificateServiceFileDescriptorSupplier
      extends IstioCertificateServiceBaseDescriptorSupplier {
    IstioCertificateServiceFileDescriptorSupplier() {}
  }

  private static final class IstioCertificateServiceMethodDescriptorSupplier
      extends IstioCertificateServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    IstioCertificateServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (IstioCertificateServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new IstioCertificateServiceFileDescriptorSupplier())
              .addMethod(getCreateCertificateMethod())
              .build();
        }
      }
    }
    return result;
  }
}
