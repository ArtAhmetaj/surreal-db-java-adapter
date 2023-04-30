package com.surrealdb.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.surrealdb.database.exceptions.InvalidArgumentsException;
import com.surrealdb.database.models.DatabaseRequest;
import com.surrealdb.database.models.DatabaseResponse;
import com.surrealdb.database.models.Patch;
import com.surrealdb.websocket.RpcClient;
import com.surrealdb.websocket.RpcClientImpl;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class SurrealDbAdapter {

    public SurrealDbAdapter(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }

    public SurrealDbAdapter(URI uri) {
        this.rpcClient = new RpcClientImpl(uri, new ObjectMapper());

    }

    private final RpcClient rpcClient;


    public void connect() {
        this.rpcClient.connect();
    }

    public void close() {
        this.rpcClient.close();
    }

    public CompletableFuture<DatabaseResponse> use(String database, String nameSpace) {
        return send(DatabaseCommandFactory.getParamsForUse(database, nameSpace));
    }

    public CompletableFuture<DatabaseResponse> info() {
        return send(DatabaseCommandFactory.getParamsForInfo());
    }

    public CompletableFuture<DatabaseResponse> signUp(String user, String password) {
        return send(DatabaseCommandFactory.getParamsForSignup(user, password));
    }

    public CompletableFuture<DatabaseResponse> signIn(String user, String password) {
        return send(DatabaseCommandFactory.getParamsForSignIn(user, password));
    }

    public CompletableFuture<DatabaseResponse> invalidate() {
        return send(DatabaseCommandFactory.getParamsForInvalidate());
    }

    public CompletableFuture<DatabaseResponse> authenticate(String token) {
        return send(DatabaseCommandFactory.getParamsForAuthenticate(token));
    }

    public CompletableFuture<DatabaseResponse> live(String table) {
        return send(DatabaseCommandFactory.getParamsForLive(table));
    }

    public CompletableFuture<DatabaseResponse> kill(String query) {
        return send(DatabaseCommandFactory.getParamsForKill(query));
    }

    public <T> CompletableFuture<DatabaseResponse> let(String key, T val) {
        return send(DatabaseCommandFactory.getParamsForLet(key, val));
    }

    public <T> CompletableFuture<DatabaseResponse> query(String sql, T vars) {
        return send(DatabaseCommandFactory.getParamsForQuery(sql, vars));
    }

    public CompletableFuture<DatabaseResponse> select(String what) {
        return send(DatabaseCommandFactory.getParamsForSelect(what));
    }

    public <T> CompletableFuture<DatabaseResponse> create(String thing, T data) {
        return send(DatabaseCommandFactory.getParamsForCreate(thing, data));
    }

    public <T> CompletableFuture<DatabaseResponse> update(String what, T data) {
        return send(DatabaseCommandFactory.getParamsForUpdate(what, data));
    }


    public <T> CompletableFuture<DatabaseResponse> change(String what, T data) {
        return send(DatabaseCommandFactory.getParamsForChange(what, data));
    }

    public CompletableFuture<DatabaseResponse> modify(String what, List<Patch> data) {
        return send(DatabaseCommandFactory.getParamsForModify(what, data));
    }

    public CompletableFuture<DatabaseResponse> delete(String what) {
        return send(DatabaseCommandFactory.getParamsForDelete(what));
    }


    private CompletableFuture<DatabaseResponse> send(
            DatabaseRequest request) {
        try {
            var response = rpcClient.sendSocketRequest(request, DatabaseResponse.class);
            if (Objects.equals(request.getMethod(), "delete")) {
                return CompletableFuture.completedFuture(null);
            }
            return response;
        } catch (JsonProcessingException e) {
            throw new InvalidArgumentsException(e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        throw new InvalidArgumentsException("Invalid arguments");
    }


}
