package com.surrealdb.database.exceptions;

public class ProcessingQueryException extends RuntimeException{
    public ProcessingQueryException() {
        super("Error processing query");
    }
}
