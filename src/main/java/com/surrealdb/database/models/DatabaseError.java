package com.surrealdb.database.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DatabaseError {
    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "DatabaseError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    @JsonCreator
    public DatabaseError(@JsonProperty("code") int code, @JsonProperty("message") String message) {
        this.code = code;
        this.message = message;
    }
}
