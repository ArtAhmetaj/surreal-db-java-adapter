package com.surrealdb.database.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DatabaseResponse {
    private final String id;
    private final DatabaseError error;
    private final Object result;

    public Object getId() {
        return id;
    }

    @Override
    public String toString() {
        return "DatabaseResponse{" +
                "id='" + id + '\'' +
                ", error=" + error +
                ", result=" + result +
                '}';
    }

    public DatabaseError getError() {
        return error;
    }

    public Object getResult() {
        return result;
    }

    @JsonCreator
    public DatabaseResponse(@JsonProperty("id") String id,
                            @JsonProperty("error") DatabaseError error,
                            @JsonProperty("result") Object result) {
        this.id = id;
        this.error = error;
        this.result = result;
    }

}
