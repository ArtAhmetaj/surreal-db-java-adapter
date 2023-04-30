package com.surrealdb.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class RpcClientImpl extends WebSocketClient implements RpcClient {
    private static final Logger logger = Logger.getLogger("RpcClientImpl");
    private static final int NORMAL_CLOSURE_CODE = 1000;
    private static final int DEFAULT_TIMEOUT = 30;
    private final BlockingQueue<String> responses;

    public RpcClientImpl(URI serverUri) {
        super(serverUri);
        this.responses = new LinkedBlockingQueue<>(1);
        this.setConnectionLostTimeout(DEFAULT_TIMEOUT);
    }


    @Override
    public String sendSocketRequest(String request) throws InterruptedException {
        this.send(request);
        return responses.take();
    }



    @Override
    public void setTimeout(int timeout) {
        this.setConnectionLostTimeout(timeout);
    }

    @Override
    public void close() {
        this.close(NORMAL_CLOSURE_CODE);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        logger.log(new LogRecord(Level.FINE, "Connection established"));
    }

    @Override
    public void onMessage(String s) {
        this.responses.add(s);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        String closeInitiator = remote ? "remote peer" : "us";
        var response = String.format("Connection closed by %s, Code: %s, Reason: %s",
                closeInitiator,
                code,
                reason);
        logger.log(new LogRecord(Level.FINE, response));
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
