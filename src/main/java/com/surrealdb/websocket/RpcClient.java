package com.surrealdb.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface RpcClient {
    <T> T send(Object id, String method, Object[] params,Class<T> target) throws JsonProcessingException, InterruptedException;
    void connect();
    void setTimeout(int timeout);

    void close();
}
