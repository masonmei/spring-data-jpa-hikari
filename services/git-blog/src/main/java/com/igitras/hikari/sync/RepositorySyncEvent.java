package com.igitras.hikari.sync;

import java.io.Serializable;

/**
 * @author mason
 */
public class RepositorySyncEvent implements Serializable {

    private static final long serialVersionUID = 1510550190249047515L;

    private Type type;
    private RepositorySyncTaskConfig.BasicTaskConfig config;

    public Type getType() {
        return type;
    }

    public RepositorySyncEvent setType(Type type) {
        this.type = type;
        return this;
    }

    public RepositorySyncTaskConfig.BasicTaskConfig getConfig() {
        return config;
    }

    public RepositorySyncEvent setConfig(RepositorySyncTaskConfig config) {
        this.config = new RepositorySyncTaskConfig.BasicTaskConfig(config);
        return this;
    }

    public enum Type {
        RELOAD,
        CREATE,
        UPDATE,
        REMOVE
    }
}
