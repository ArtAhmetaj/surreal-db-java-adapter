package com.surrealdb.websocket.models;

public class RpcResponse<T> {
   private final Object id;
   private final RpcError error;
   private final T result;

    public Object getId() {
        return id;
    }

    public RpcError getError() {
        return error;
    }

    public T getResult() {
        return result;
    }

    public RpcResponse(Object id, RpcError error, T result) {
        this.id = id;
        this.error = error;
        this.result = result;
    }

    public static final class RpcResponseBuilder<T> {
        private Object id;
        private RpcError error;
        private T result;

        private RpcResponseBuilder() {
        }

        public static <T> RpcResponseBuilder<T> aRpcResponse() {
            return new RpcResponseBuilder<>();
        }

        public  RpcResponseBuilder<T> setId(Object id) {
            this.id = id;
            return this;
        }

        public RpcResponseBuilder<T> setError(RpcError error) {
            this.error = error;
            return this;
        }

        public RpcResponseBuilder<T> setResult(T result) {
            this.result = result;
            return this;
        }

        public RpcResponse<T> build() {
            return new RpcResponse<T>(id, error, result);
        }
    }
}
