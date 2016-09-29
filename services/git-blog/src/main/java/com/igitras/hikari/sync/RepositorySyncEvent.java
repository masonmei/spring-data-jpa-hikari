package com.igitras.hikari.sync;

import com.igitras.hikari.domain.SyncRepo;

import java.io.Serializable;

/**
 * @author mason
 */
public class RepositorySyncEvent implements Serializable {

    private static final long serialVersionUID = 1510550190249047515L;

    private Type type;
    private SyncRepo syncRepo;

    public Type getType() {
        return type;
    }

    public RepositorySyncEvent setType(Type type) {
        this.type = type;
        return this;
    }

    public SyncRepo getSyncRepo() {
        return syncRepo;
    }

    public RepositorySyncEvent setSyncRepo(SyncRepo syncRepo) {
        this.syncRepo = syncRepo;
        return this;
    }

    public enum Type {
        RELOAD,
        CREATE,
        UPDATE,
        REMOVE
    }
}
