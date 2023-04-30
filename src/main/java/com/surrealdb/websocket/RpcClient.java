package com.surrealdb.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.CompletableFuture;

public interface RpcClient {
    String send(Object request) throws JsonProcessingException, InterruptedException;

    void connect();

     CompletableFuture<String> sendAsync(Object request) throws JsonProcessingException, InterruptedException;

    void setTimeout(int timeout);

    void close();
}
