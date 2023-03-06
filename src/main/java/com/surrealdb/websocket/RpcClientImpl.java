package com.surrealdb.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.surrealdb.websocket.models.RpcRequest.RpcRequestBuilder.aRpcRequest;

public class RpcClientImpl extends WebSocketClient implements RpcClient {

    private static final int NORMAL_CLOSURE_CODE = 1000;
    private static final int DEFAULT_TIMEOUT = 30;
    private final BlockingQueue<Object> responses;
    private final ObjectMapper objectMapper;

    public RpcClientImpl(URI serverUri, ObjectMapper objectMapper) {
        super(serverUri);
        this.objectMapper = objectMapper;
        this.responses = new LinkedBlockingQueue<>(1);
        this.setConnectionLostTimeout(DEFAULT_TIMEOUT);
    }

    @Override
    public <T> T send(Object id, String method, Object[] params, Class<T> target) throws JsonProcessingException, InterruptedException {
        var rpcRequest = aRpcRequest()
                .setId(id)
                .setMethod(method)
                .setParams(params)
                .build();

        var serializedObject = objectMapper.writeValueAsString(rpcRequest);
        this.send(serializedObject);
        var result = responses.take();
        return objectMapper.convertValue(result, target);
    }

    @Override
    public void setTimeout(int timeout) {
        this.setConnectionLostTimeout(timeout);
    }

    @Override
    public void close() {
        this.close(NORMAL_CLOSURE_CODE);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Connection established");
    }

    @Override
    public void onMessage(String s) {
        try {
            var deserializedObject = objectMapper.readValue(s, Map.class);
            this.responses.add(deserializedObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println(
                "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: "
                        + reason);
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
