package com.surrealdb.websocket.models;

public class RpcNotification {
    private final Object id;
    private final String method;
    private final Object[] params;

    public Object getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public Object[] getParams() {
        return params;
    }

    public RpcNotification(Object id, String method, Object[] params) {
        this.id = id;
        this.method = method;
        this.params = params;
    }

    public static final class RpcNotificationBuilder {
        private Object id;
        private String method;
        private Object[] params;

        private RpcNotificationBuilder() {
        }

        public static RpcNotificationBuilder aRpcNotification() {
            return new RpcNotificationBuilder();
        }

        public RpcNotificationBuilder setId(Object id) {
            this.id = id;
            return this;
        }

        public RpcNotificationBuilder setMethod(String method) {
            this.method = method;
            return this;
        }

        public RpcNotificationBuilder setParams(Object[] params) {
            this.params = params;
            return this;
        }

        public RpcNotification build() {
            return new RpcNotification(id, method, params);
        }
    }
}
