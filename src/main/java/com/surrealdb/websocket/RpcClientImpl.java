package com.surrealdb.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.surrealdb.database.exceptions.InvalidArgumentsException;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class RpcClientImpl extends WebSocketClient implements RpcClient {
    private static final Logger logger = Logger.getLogger("RpcClientImpl");
    private static final int NORMAL_CLOSURE_CODE = 1000;
    private static final int DEFAULT_TIMEOUT = 30;
    private final Map<String, CompletableFuture<String>> responses;


    private final ObjectMapper objectMapper;

    public RpcClientImpl(URI serverUri, ObjectMapper objectMapper) {
        super(serverUri);
        this.objectMapper = objectMapper;
        this.responses = new HashMap<>();
        this.setConnectionLostTimeout(DEFAULT_TIMEOUT);
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> CompletableFuture<T> sendSocketRequest(RpcObject request, Class<T> type) throws JsonProcessingException {
        this.send(objectMapper.writeValueAsString(request));
        this.responses.put(request.getId(), new CompletableFuture<>());
        return (CompletableFuture<T>) this.responses.get(request.getId());
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
        logger.log(new LogRecord(Level.FINE, "Connection established"));
    }

    @Override
    public void onMessage(String s) {
        try {
            Map<String,Object> rpcObject = objectMapper.readValue(s, new TypeReference<>() {
            });
            var completable = this.responses.get(rpcObject.get("id"));
            completable.complete(s);
        } catch (JsonProcessingException e) {
            throw new InvalidArgumentsException("No id found on response");
        }

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        String closeInitiator = remote ? "remote peer" : "us";
        var response = String.format("Connection closed by %s, Code: %s, Reason: %s",
                closeInitiator,
                code,
                reason);
        logger.log(new LogRecord(Level.FINE, response));
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
