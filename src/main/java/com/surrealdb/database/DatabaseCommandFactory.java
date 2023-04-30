package com.surrealdb.database;

import com.surrealdb.database.models.DatabaseRequest;

import java.util.Map;

public class DatabaseCommandFactory {


    public static DatabaseRequest getParamsForUse(String database, String nameSpace) {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(16),
                        false,
                        "use",
                        new Object[]{Map.of("database", database, "namespace", nameSpace)}
                );
    }

    public static DatabaseRequest getParamsForInfo() {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(16),
                        false,
                        "info",
                        null
                );
    }

    public static DatabaseRequest getParamsForSignup(String user, String password) {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(16),
                        false,
                        "signup",
                        new Object[]{Map.of("user", user, "pass", password)}
                );
    }

    public static DatabaseRequest getParamsForSignIn(String user, String password) {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(16),
                        false,
                        "signin",
                        new Object[]{Map.of("user", user, "pass", password)}
                );
    }



}
