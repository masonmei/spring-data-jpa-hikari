package com.igitras.hikari.sync.message;

import com.igitras.hikari.sync.RepositorySyncEvent;

/**
 * Repository Synchronise event publisher.
 *
 * @author mason
 */
public interface RepositorySyncEventPublisher {

    /**
     * Publish the synchronising event.
     *
     * @param event synchronise event
     * @param <T>   event type
     */
    <T extends RepositorySyncEvent> void publish(T event);
}
