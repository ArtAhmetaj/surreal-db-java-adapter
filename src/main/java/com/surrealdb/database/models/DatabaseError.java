package com.surrealdb.database.models;

public class DatabaseError {
    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public DatabaseError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
