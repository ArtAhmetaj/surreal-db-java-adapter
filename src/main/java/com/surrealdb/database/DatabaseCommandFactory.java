package com.surrealdb.database;

import com.surrealdb.database.models.DatabaseRequest;
import com.surrealdb.database.models.Patch;

import java.util.List;
import java.util.Map;

public class DatabaseCommandFactory {
    //TODO: generic types can be replaced with specific models
    private DatabaseCommandFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static DatabaseRequest getParamsForUse(String database, String nameSpace) {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(),
                        false,
                        "use",
                        new Object[]{database, nameSpace}
                );
    }

    public static DatabaseRequest getParamsForInfo() {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(),
                        false,
                        "info",
                        new Object[]{}
                );
    }

    public static DatabaseRequest getParamsForSignup(String user, String password) {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(),
                        false,
                        "signup",
                        new Object[]{Map.of("user", user, "pass", password)}
                );
    }

    public static DatabaseRequest getParamsForSignIn(String user, String password) {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(),
                        false,
                        "signin",
                        new Object[]{Map.of("user", user, "pass", password)}
                );
    }

    public static DatabaseRequest getParamsForInvalidate() {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(),
                        false,
                        "invalidate",
                        new Object[]{}
                );
    }

    public static DatabaseRequest getParamsForAuthenticate(String token) {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(),
                        false,
                        "authenticate",
                        new Object[]{token}
                );
    }

    public static DatabaseRequest getParamsForLive(String table) {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(),
                        false,
                        "live",
                        new Object[]{table}
                );
    }

    public static DatabaseRequest getParamsForKill(String query) {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(),
                        false,
                        "kill",
                        new Object[]{query}
                );
    }

    public static <T> DatabaseRequest getParamsForLet(String let, T val) {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(),
                        false,
                        "let",
                        new Object[]{let, val}
                );
    }

    public static <T> DatabaseRequest getParamsForQuery(String sql, T vars) {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(),
                        false,
                        "query",
                        new Object[]{sql, vars}
                );
    }

    public static DatabaseRequest getParamsForSelect(String what) {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(),
                        false,
                        "select",
                        new Object[]{what}
                );
    }

    public static <T> DatabaseRequest getParamsForCreate(String thing, T data) {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(),
                        false,
                        "create",
                        new Object[]{thing, data}
                );
    }

    public static <T> DatabaseRequest getParamsForUpdate(String what, T data) {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(),
                        false,
                        "update",
                        new Object[]{what,data}
                );
    }

    public static <T> DatabaseRequest getParamsForChange(String what, T data) {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(),
                        false,
                        "change",
                        new Object[]{what,data}
                );
    }

    public static DatabaseRequest getParamsForModify(String what, List<Patch> data) {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(),
                        false,
                        "modify",
                        new Object[]{what,data}
                );
    }

    public static DatabaseRequest getParamsForDelete(String what) {
        return
                new DatabaseRequest(
                        DatabaseHelper.generateXid(),
                        false,
                        "delete",
                        new Object[]{what}
                );
    }


}
