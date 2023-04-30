package com.surrealdb.database.models;

public class DatabaseResponse{
   private final String id;
   private final DatabaseError error;
   private final Object result;

    public Object getId() {
        return id;
    }

    public DatabaseError getError() {
        return error;
    }

    public Object getResult() {
        return result;
    }

    public DatabaseResponse(String id, DatabaseError error, Object result) {
        this.id = id;
        this.error = error;
        this.result = result;
    }

    public static final class RpcResponseBuilder {
        private String id;
        private DatabaseError error;
        private Object result;

        private RpcResponseBuilder() {
        }

        public static  RpcResponseBuilder aRpcResponse() {
            return new RpcResponseBuilder();
        }

        public  RpcResponseBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public RpcResponseBuilder setError(DatabaseError error) {
            this.error = error;
            return this;
        }

        public RpcResponseBuilder setResult(Object result) {
            this.result = result;
            return this;
        }

        public Object DatabaseResponseBuild() {
            return new DatabaseResponse(id, error, result);
        }
    }
}
