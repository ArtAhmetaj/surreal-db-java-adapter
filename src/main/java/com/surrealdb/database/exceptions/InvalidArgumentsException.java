package com.surrealdb.database.exceptions;

public class InvalidArgumentsException  extends RuntimeException{
    public InvalidArgumentsException(String message) {
        super(message);
    }
}
