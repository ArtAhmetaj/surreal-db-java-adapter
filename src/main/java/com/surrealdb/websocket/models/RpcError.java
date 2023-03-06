package com.surrealdb.websocket.models;

public class RpcError {
    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public RpcError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
