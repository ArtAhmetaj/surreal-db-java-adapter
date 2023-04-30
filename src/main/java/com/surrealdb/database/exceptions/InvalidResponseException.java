package com.surrealdb.database.exceptions;

public class InvalidResponseException extends RuntimeException{
    public InvalidResponseException() {
        super("Invalid SurrealDB response");
    }
}
