package com.surrealdb.database.models;

public class Patch {
    public String getOperation() {
        return operation;
    }

    public String getPath() {
        return path;
    }

    public Object getValue() {
        return value;
    }

    private final String operation;
    private final String path;
    private final Object value;

    public Patch(String operation, String path, Object value) {
        this.operation = operation;
        this.path = path;
        this.value = value;
    }
}
