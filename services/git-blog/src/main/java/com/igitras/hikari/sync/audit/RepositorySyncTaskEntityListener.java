package com.igitras.hikari.sync.audit;

import static com.igitras.hikari.sync.RepositorySyncEvent.Type.CREATE;
import static com.igitras.hikari.sync.RepositorySyncEvent.Type.REMOVE;
import static com.igitras.hikari.sync.RepositorySyncEvent.Type.UPDATE;

import com.igitras.hikari.sync.RepositorySyncEvent;
import com.igitras.hikari.sync.message.RepositorySyncEventPublisher;
import com.igitras.hikari.sync.RepositorySyncTaskConfig;
import com.igitras.hikari.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

/**
 * Repository Sync Task Event Listener.
 *
 * @author mason
 */
@Configurable
public class RepositorySyncTaskEntityListener {
    private static final Logger LOG = LoggerFactory.getLogger(RepositorySyncTaskEntityListener.class);

    @PostPersist
    public <T extends RepositorySyncTaskConfig> void postPersist(T target) {
        LOG.debug("Post Persist processing: {}", target);
        RepositorySyncEventPublisher publisher = SpringContextUtil.getBean(RepositorySyncEventPublisher.class);
        if (publisher != null) {
            publisher.publish(new RepositorySyncEvent().setType(CREATE)
                    .setConfig(new RepositorySyncTaskConfig.BasicTaskConfig(target)));
        }

    }

    @PostUpdate
    public <T extends RepositorySyncTaskConfig> void postUpdate(T target) {
        LOG.debug("Post Update processing: {}", target);
        RepositorySyncEventPublisher publisher = SpringContextUtil.getBean(RepositorySyncEventPublisher.class);
        if (publisher != null) {
            publisher.publish(new RepositorySyncEvent().setType(UPDATE)
                    .setConfig(new RepositorySyncTaskConfig.BasicTaskConfig(target)));
        }
    }

    @PostRemove
    public <T extends RepositorySyncTaskConfig> void postDelete(T target) {
        LOG.debug("Post Remove processing: {}", target);
        RepositorySyncEventPublisher publisher = SpringContextUtil.getBean(RepositorySyncEventPublisher.class);
        if (publisher != null) {
            publisher.publish(new RepositorySyncEvent().setType(REMOVE)
                    .setConfig(new RepositorySyncTaskConfig.BasicTaskConfig(target)));
        }
    }
}
