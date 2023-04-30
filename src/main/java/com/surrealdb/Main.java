package com.surrealdb;

import com.surrealdb.database.SurrealDbAdapter;

import java.net.URI;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        var dbAdapter = new SurrealDbAdapter(URI.create("ws://localhost:8000/rpc"));
        dbAdapter.connect();
        dbAdapter.signIn("root", "root");
        dbAdapter.use("test","test");
        var response = dbAdapter.create("user",Map.of("name","test"));
        System.out.println(response);
        dbAdapter.close();

    }
}
