package com.surrealdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surrealdb.database.SurrealDbAdapter;
import com.surrealdb.websocket.RpcClientImpl;

import java.net.URI;
import java.util.Map;

public class Main {

    public static void main(String[] args){

        var client = new RpcClientImpl(URI.create("ws://localhost:8000/rpc"), new ObjectMapper());
        var dbAdapter = new SurrealDbAdapter(client);
        dbAdapter.connect();
        var response = dbAdapter.signUp("root","root");
        System.out.println(response);
    }
}
