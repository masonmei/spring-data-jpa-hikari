package com.igitras.hikari.sync.message;

import static com.igitras.hikari.service.repos.RepoSyncTaskContextFactory.buildContext;
import static com.igitras.hikari.utils.FileUtil.resolveFolder;

import com.igitras.hikari.config.AppProperties;
import com.igitras.hikari.service.repos.RepositorySyncService;
import com.igitras.hikari.sync.RepositorySyncEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.Topic;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Repository Sync Event Listener.
 *
 * @author mason
 */
public class RepositorySyncEventListener implements MessageListener, InitializingBean {
    private static final Logger LOG = LoggerFactory.getLogger(RepositorySyncEventListener.class);

    private Topic topic;
    private RedisTemplate<String, RepositorySyncEvent> redisTemplate;

    private RepositorySyncService gitRepositorySyncService;
    private AppProperties appProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(topic, "Topic must not be null.");
        Assert.notNull(gitRepositorySyncService, "Repository Sync Service must not be null.");
        Assert.notNull(appProperties, "App Properties must not be null.");
    }

    public RepositorySyncEventListener setTopic(Topic topic) {
        this.topic = topic;
        return this;
    }

    public RepositorySyncEventListener setRedisTemplate(RedisTemplate<String, RepositorySyncEvent> redisTemplate) {
        this.redisTemplate = redisTemplate;
        return this;
    }

    public RepositorySyncEventListener setGitRepositorySyncService(RepositorySyncService gitRepositorySyncService) {
        this.gitRepositorySyncService = gitRepositorySyncService;
        return this;
    }

    public RepositorySyncEventListener setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
        return this;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String topic = new String(pattern);
        if (!this.topic.getTopic()
                .equals(topic)) {
            return;
        }

        try {
            RepositorySyncEvent syncEvent = (RepositorySyncEvent) redisTemplate.getValueSerializer()
                    .deserialize(message.getBody());
            dispatchEvent(syncEvent);
        } catch (FileNotFoundException e) {
            LOG.warn("Dispatch event failed.");
        }
    }

    private void dispatchEvent(RepositorySyncEvent syncEvent) throws FileNotFoundException {
        File baseFolder = resolveFolder(appProperties.getDownloadFolder());

        switch (syncEvent.getType()) {
            case UPDATE:
                // DO NOTHING NOW
                break;
            case CREATE:
                gitRepositorySyncService.addTask(buildContext(baseFolder, syncEvent.getSyncRepo()));
                break;
            case RELOAD:
                break;
            case REMOVE:
                gitRepositorySyncService.removeTask(buildContext(baseFolder, syncEvent.getSyncRepo()));
                break;
            default:
                break;
        }
    }
}
