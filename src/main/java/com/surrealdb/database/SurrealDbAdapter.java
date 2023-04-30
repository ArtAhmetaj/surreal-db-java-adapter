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

public class SurrealDbAdapter {

    public SurrealDbAdapter(RpcClient rpcClient, ObjectMapper objectMapper) {
        this.rpcClient = rpcClient;
        this.objectMapper = objectMapper;
    }

    public SurrealDbAdapter(URI uri) {
        this.rpcClient = new RpcClientImpl(uri);
        this.objectMapper = new ObjectMapper();

    }

    private final RpcClient rpcClient;

    private final ObjectMapper objectMapper;

    public void connect() {
        this.rpcClient.connect();
    }

    public void close() {
        this.rpcClient.close();
    }

    public DatabaseResponse use(String database, String nameSpace) {
        return send(DatabaseCommandFactory.getParamsForUse(database, nameSpace));
    }

    public DatabaseResponse info() {
        return send(DatabaseCommandFactory.getParamsForInfo());
    }

    public DatabaseResponse signUp(String user, String password) {
        return send(DatabaseCommandFactory.getParamsForSignup(user, password));
    }

    public DatabaseResponse signIn(String user, String password) {
        return send(DatabaseCommandFactory.getParamsForSignIn(user, password));
    }

    public DatabaseResponse invalidate() {
        return send(DatabaseCommandFactory.getParamsForInvalidate());
    }

    public DatabaseResponse authenticate(String token) {
        return send(DatabaseCommandFactory.getParamsForAuthenticate(token));
    }

    public DatabaseResponse live(String table) {
        return send(DatabaseCommandFactory.getParamsForLive(table));
    }

    public DatabaseResponse kill(String query) {
        return send(DatabaseCommandFactory.getParamsForKill(query));
    }

    public <T> DatabaseResponse let(String key, T val) {
        return send(DatabaseCommandFactory.getParamsForLet(key, val));
    }

    //TODO: might be a map, the golang library implementation is unclear
    public <T> DatabaseResponse query(String sql, T vars) {
        return send(DatabaseCommandFactory.getParamsForQuery(sql, vars));
    }

    public DatabaseResponse select(String what) {
        return send(DatabaseCommandFactory.getParamsForSelect(what));
    }

    public <T> DatabaseResponse create(String thing, T data) {
        return send(DatabaseCommandFactory.getParamsForCreate(thing, data));
    }

    public <T> DatabaseResponse update(String what, T data) {
        return send(DatabaseCommandFactory.getParamsForUpdate(what, data));
    }


    public <T> DatabaseResponse change(String what, T data) {
        return send(DatabaseCommandFactory.getParamsForChange(what, data));
    }

    public DatabaseResponse modify(String what, List<Patch> data) {
        return send(DatabaseCommandFactory.getParamsForModify(what, data));
    }

    public DatabaseResponse delete(String what) {
        return send(DatabaseCommandFactory.getParamsForDelete(what));
    }


    private DatabaseResponse send(
            DatabaseRequest request) {
        try {
            var response = rpcClient.sendSocketRequest(objectMapper.writeValueAsString(request));
            if (Objects.equals(request.getMethod(), "delete")) {
                return null;
            }
            return objectMapper.readValue(response, DatabaseResponse.class);
        } catch (JsonProcessingException e) {
            throw new InvalidArgumentsException(e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        throw new InvalidArgumentsException("Invalid arguments");
    }


}
