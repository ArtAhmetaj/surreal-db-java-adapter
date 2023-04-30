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

//    public Object invalidate() {
//        return send("invalidate");
//    }
//
//    public Object authenticate(String token) {
//        return send("authenticate", token);
//    }
//
//    public Object live(String table) {
//        return send("live", table);
//    }
//
//    public Object kill(String query) {
//        return send("kill", query);
//    }
//
//    public Object let(String key, Object val) {
//        return send("let", key, val);
//    }
//
//    public Object query(String sql, Object vars) {
//        return send("query", sql, vars);
//    }
//
//    public Object select(String what) {
//        return send("select", what);
//    }
//
//    public Object create(String thing, Object data) {
//        return send("create", thing, data);
//    }
//
//    public Object update(String what, Object data) {
//        return send("update", what, data);
//    }
//
//    public Object change(String what, Object data) {
//        return send("change", what, data);
//    }
//
//    public Object modify(String what, List<Patch> data) {
//        return send("modify", what, data);
//    }
//
//    public Object delete(String what) {
//        return send("delete", what);
//    }


    private DatabaseResponse send(
            DatabaseRequest request) {
        try {
            var response = rpcClient.send(request);
            if(Objects.equals(request.getMethod(), "delete")) {
                return null;
            }
            return parseResponse(request.getParams(),response);
        } catch (JsonProcessingException e) {
            throw new InvalidArgumentsException(e.getMessage());
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        throw new InvalidArgumentsException("Invalid arguments");
    }

    @SuppressWarnings("unchecked")
    DatabaseResponse parseResponse(Object[] params, Object result) {
        if (params.length == 0) throw new InvalidArgumentsException("No response found");
        var arg = (String) params[0];
        if (arg.contains(":")) {
            var arrayResult = (List<Object>) result;
            if (arrayResult.isEmpty()) {
                throw new InvalidArgumentsException("No response found");
            }
            return (DatabaseResponse) arrayResult.get(0);

        }
        return (DatabaseResponse) result;
    }

}
