package com.surrealdb.database.models;

public class DatabaseRequest {
    private final String id;
    private final boolean async;

    public DatabaseRequest(String id, boolean async, String method, Object[] params) {
        this.id = id;
        this.async = async;
        this.method = method;
        this.params = params;
    }

    private final String method;

    public Object getId() {
        return id;
    }

    public boolean isAsync() {
        return async;
    }

    public String getMethod() {
        return method;
    }

    public Object[] getParams() {
        return params;
    }

    private final Object[] params;

    public static final class RpcRequestBuilder {
        private String id;
        private boolean async;
        private String method;
        private Object[] params;

        private RpcRequestBuilder() {
        }

        public static RpcRequestBuilder aRpcRequest() {
            return new RpcRequestBuilder();
        }

        public RpcRequestBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public RpcRequestBuilder setAsync(boolean async) {
            this.async = async;
            return this;
        }

        public RpcRequestBuilder setMethod(String method) {
            this.method = method;
            return this;
        }

        public RpcRequestBuilder setParams(Object[] params) {
            this.params = params;
            return this;
        }

        public DatabaseRequest build() {
            return new DatabaseRequest(id, async, method, params);
        }
    }
}

