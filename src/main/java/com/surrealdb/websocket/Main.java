package com.surrealdb.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;

public class Main {

    public static void main(String[] args){
        var client = new RpcClientImpl(URI.create("ws://localhost:8000/rpc"), new ObjectMapper());
        client.connect();
    }
}
