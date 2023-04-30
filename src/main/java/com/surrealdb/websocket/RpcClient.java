package com.surrealdb.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.surrealdb.database.models.DatabaseRequest;

import java.util.concurrent.CompletableFuture;

public interface RpcClient {
    <T> CompletableFuture<T> sendSocketRequest(RpcObject request,Class<T> type) throws JsonProcessingException, InterruptedException;

    void connect();

    void setTimeout(int timeout);

    void close();
}
