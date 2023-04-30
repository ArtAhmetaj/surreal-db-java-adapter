package com.surrealdb.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.CompletableFuture;

public interface RpcClient {
    String sendSocketRequest(String request) throws JsonProcessingException, InterruptedException;

    void connect();

    void setTimeout(int timeout);

    void close();
}
