package com.igitras.hikari.sync.message;

import com.igitras.hikari.sync.RepositorySyncEvent;

/**
 * @author mason
 */
public interface RepositorySyncEventPublisher {

    <T extends RepositorySyncEvent> void publish(T event);
}
